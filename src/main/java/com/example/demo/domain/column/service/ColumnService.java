package com.example.demo.domain.column.service;

import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.BoardRepository;
import com.example.demo.domain.board.BoardService;
import com.example.demo.domain.column.dto.RequestColumnDto;
import com.example.demo.domain.column.dto.ResponseColumnDto;
import com.example.demo.domain.column.dto.ResponseFindColumnDto;
import com.example.demo.domain.column.entity.BoardColumn;
import com.example.demo.domain.column.repository.ColumnRepository;
import com.example.demo.domain.permission.entity.PermissionType;
import com.example.demo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {


    private final ColumnRepository columnRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;


    // 컬럼 생성 (보드에 컬럼 생성)
    @Transactional
    public ResponseColumnDto createColumn(Long boardId, RequestColumnDto requestDto, User user) {
        // 변경 후 : 보드 ID로 특정 Board 객체를 조회 => BoardColumn을 생성
        Board board = boardService.findByBoardId(boardId);

        // 권한 체크: MANAGER 권한을 가진 사용자만 컬럼 생성 허용
        if (user.getPermission() != PermissionType.MANAGER) {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }

        // 상태 이름 필수 데이터 체크
        if (requestDto.getName() == null || requestDto.getName().trim().isEmpty()) {
            throw new CustomException(ErrorCode.STATUS_NAME_NOT_FOUND);
        }

        // 이미 존재하는 상태 이름 체크 (보드 내에서 유일해야 함)
        if (columnRepository.existsByNameAndBoard(requestDto.getName(), board)) {
            throw new CustomException(ErrorCode.DUPLICATE_STATUS_NAME);
        }

        // 새 컬럼의 순서 결정 (보드 내에서의 순서)
        Long maxOrder = columnRepository.findMaxOrdersByBoard(board).orElse(0L);
        Long newOrder = maxOrder + 1;

        // BoardColumn 객체 생성
        BoardColumn boardColumn = new BoardColumn(requestDto.getName(), newOrder, board);

        // 저장 및 반환
        boardColumn = columnRepository.save(boardColumn);
        return convertToResponseDto(boardColumn);
    }



    // 컬럼 삭제
    // ✅ 삭제할 때 ‘삭제하는 경우 연결된 데이터가 전부 삭제됩니다. 정말 삭제하시겠습니까?’ => 프론트에서 처리
    @Transactional
    public void deleteColumn(Long boardId, Long columnId, User user) {
        // 변경 후 : 보드 ID와 컬럼 ID를 모두 사용하여 해당 보드에 속한 컬럼만을 삭제
        // 특정 보드에 특정 컬럼만 삭제하여 다른 보드의 컬럼을 삭제하는 것을 방지
        Board board = boardService.findByBoardId(boardId);

        if (user.getPermission() != PermissionType.MANAGER) {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }

        BoardColumn boardColumn = (BoardColumn) columnRepository.findByIdAndBoard(columnId, board)
                .orElseThrow(() -> new CustomException(ErrorCode.COLUMN_ALREADY_DELETED_OR_NOT_EXIST));

        columnRepository.delete(boardColumn);
    }


    // 컬럼 순서 이동 (실제 순서 변경하는 로직)
    // 성공 조건 :
    // (1). 자유로운 순서 변경 : reorderColumns 메소드로 구현
    // (2). 새로고침 후 유지 : DB에 저장되므로 구현

    // 현재 순서 이동 방식은 '확정' 버튼을 누르는 방식에 가까움,
    // ➡️드래그 앤 드롭 시 마다 순서를 변경하려면 프론트엔드에서 각 이동마다 API를 호출하도록 구현



    /*
    📢 드래그 앤 드롭 프로세스 :
    (1). 사용자가 드래그 앤 드롭으로 컬럼 순서를 변경 => 프론트엔드에서 처리
    (2). 사용자가 '확정' 버튼을 클릭시, 프론트엔드는 새로운컬럼 순서(columnIds)를 백엔드 API(/{board_id}/reorder)로 전송
    (3). 컨트롤러는 요청을 받아서 ColumnService에 reorderColumns 메소드를 호출
    (4). reorderColumns 메소드는

            - 사용자 권한 확인
            - 보드 ID로 해당 보드의 모든 컬럼 조회
            - 입력된 컬럼 ID리스트 유효성 검사
            - 각 컬럼의 순서를 새로운 순서에 맞게 UPDATE
            - 변경된 컬럼들을 DB에 저장
            - 업데이트된 컬럼 정보를 DTO로 변환하여 반환

     (5). 컨트롤러는 업데이트된 컬럼 정보를 클라이언트에 반환
     (6). 프론트엔드는 반환된 정보로 UI를 업뎃
     (7). 사용자가 페이지를 새로고침해도 DB에 저장된 새로운 순서대로 컬럼이 표시
            => findAllByBoardOrderByOrder 메서드가 컬럼을 순서대로 조회하기 때문


     📢 기대효과

            - 컬럼 순서 자유롭게 변경 가능
            - 새로고침 후에도 변경된 순서 유지
            - '확정' 버튼(백엔드 API reorder) 을 통해 순서 변경을 적용
     */

    // 변경 전 : 모든 보드의 컬럼을 대상으로 재정렬
    // 변경 후 : 특정 보드의 컬람만을 대상으로 재정렬
    // => 특정 보드 내에서만 컬럼 순서를 변경하기 때문에 다른 보드의 컬럼 순서에 영향 X
    @Transactional
    public List<ResponseColumnDto> reorderColumns(Long boardId, List<Long> columnIds, User user) {

        // 권한 체크
        if (user.getPermission() != PermissionType.MANAGER) {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }

        Board board = boardService.findByBoardId(boardId);
        List<BoardColumn> boardColumns = columnRepository.findAllByBoardOrdersByOrder(board);

        // 입력된 컬럼 ID들의 유효성을 검사
        if (boardColumns.size() != columnIds.size() || !boardColumns.stream().map(BoardColumn::getId).collect(Collectors.toSet()).equals(new HashSet<>(columnIds))) {
            throw new CustomException(ErrorCode.COLUMN_IDS_NOT_VALID);
        }

        // 순서 재정렬 로직
        for (int i = 0; i < columnIds.size(); i++) {
            Long id = columnIds.get(i);
            BoardColumn boardColumn = boardColumns.stream()
                    .filter(col -> col.getId().equals(id))
                    .findFirst()
                    // 찾을 수 없는 id 값을 나타내기 위해 임식적으로 IllegalArgumentException 처리
                    .orElseThrow(() -> new IllegalArgumentException("컬럼을 찾을 수 없습니다: " + id));
            boardColumn.changeOrders((long) (i + 1));
        }

        // 변경된 컬럼들을 저장하고 DTO로 변환하여 반환
        boardColumns = columnRepository.saveAll(boardColumns);
        return boardColumns.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    // DTO 변환 메서드
    // BoardColumn 엔티티를 ColumnDto 로 변환하는 메서드
    // 엔티티 속성을 DTO에 복사
    private ResponseColumnDto convertToResponseDto(BoardColumn boardColumn) {
        return ResponseColumnDto.builder()
                .id(boardColumn.getId())
                .name(boardColumn.getName())
                .order(boardColumn.getOrders())
                .build();
    }

    public BoardColumn findStatus(String status) {
        return columnRepository.findByName(status).orElseThrow(()->new CustomException(ErrorCode.COLUMN_NOT_FOUND));
    }

    public List<ResponseFindColumnDto> findAllColumns(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("조회된 보드가 없습니다.")
        );
        List<BoardColumn> columnList = columnRepository.findAllByBoard(board);

        List<ResponseFindColumnDto> responseColumnDtoList = new ArrayList<>();
        for(BoardColumn boardColumn : columnList) {
            responseColumnDtoList.add(new ResponseFindColumnDto(boardColumn));
        }
        return responseColumnDtoList;
    }
}