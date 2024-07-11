package com.example.demo.column.service;

import com.example.demo.column.dto.ColumnDto;
import com.example.demo.column.entity.BoardColumn;
import com.example.demo.column.repository.ColumnRepository;
import com.example.demo.column.exception.UnauthorizedException;
import com.example.demo.column.exception.ColumnAlreadyExistsException;
import com.example.demo.column.exception.ColumnNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ColumnService {

    private final ColumnRepository columnRepository;

    @Autowired
    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }


    // 📢 실제 권한 체크: 현재는 임시로 hasPermission 변수를 사용
    // 실제 인증 및 권한 체크 로직으로 추후 대체 필요


    // 컬럼 생성 (보드에 컬럼 생성)
    // 성공 조건 :
    // (1). 보드에 컬럼 생성 : createColumn 메소드로 구현
    // (2). '상태 이름' 필수 데이터 : ColumnDto 에 name 필드로 구현
    @Transactional
    public ColumnDto createColumn(ColumnDto columnDto, boolean hasPermission) {
        if (!hasPermission) {
            throw new UnauthorizedException("권한이 없습니다."); // 예외 처리 : 권한 없는 사용자
        }
        if (columnRepository.existsByName(columnDto.getName())) {
            throw new ColumnAlreadyExistsException("이미 존재하는 상태 이름입니다."); // 예외 처리 : 중복 '상태 이름'
        }
        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setName(columnDto.getName());

        // 현재 최대 순서 값을 조회한 후 1을 더하는 방식
        // 컬럼이 비어있을 시 기본값 0L(아직 컬럼이 없으므로 첫 번째 컬럼의 순서는 1이 될 것)
        Long maxOrder = columnRepository.findMaxOrder().orElse(0L);
        boardColumn.setOrder(maxOrder + 1);

        boardColumn = columnRepository.save(boardColumn);
        return convertToDto(boardColumn);
    }


    // 컬럼 삭제
    // 삭제할 때 ‘삭제하는 경우 연결된 데이터가 전부 삭제됩니다. 정말 삭제하시겠습니까?’ => 프론트에서 처리
    // 취소 → 삭제 기능 수행 X
    // 확인 → 삭제 기능 수행
    @Transactional
    public void deleteColumn(Long id, boolean hasPermission) {
        if (!hasPermission) {
            throw new UnauthorizedException("권한이 없습니다."); // 예외 처리 : 권한 없는 사용자
        }
        BoardColumn boardColumn = columnRepository.findById(id)
                // 예외 처리 : 중복 '상태 이름'
                .orElseThrow(() -> new ColumnNotFoundException("이미 삭제된 컬럼이거나 존재하지 않는 컬럼입니다."));
        columnRepository.delete(boardColumn);
    }


    // 컬럼 순서 이동
    // 성공 조건 :
    // (1). 자유로운 순서 변경 : reorderColumns 메소드로 구현
    // (2). 새로고침 후 유지 : DB에 저장되므로 구현
    // 현재 순서 이동 방식은 '확정' 버튼을 누르는 방식에 가까움, 드래그 앤 드롭 시 마다 순서를 변경하려면
    // 프론트엔드에서 각 이동마다 API를 호출하도록 구현
    @Transactional
    public List<ColumnDto> reorderColumns(List<Long> columnIds, boolean hasPermission) {
        if (!hasPermission) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        List<BoardColumn> boardColumns = columnRepository.findAllById(columnIds);
        // Map을 사용하여 각 ID에 대한 BoardColumn을 빠르게 get
        Map<Long, BoardColumn> columnMap = boardColumns.stream()
                .collect(Collectors.toMap(BoardColumn::getId, Function.identity()));

        // 순서 재정렬 로직
        // 각 열의 order 속성 update
        for (int i = 0; i < columnIds.size(); i++) {
            Long id = columnIds.get(i);
            BoardColumn boardColumn = columnMap.get(id);
            if (boardColumn == null) {
                throw new ColumnNotFoundException("컬럼을 찾을 수 없습니다: " + id);
            }
            boardColumn.setOrder(Long.valueOf(i + 1)); // 순서 재정렬
        }

        // 열 저장 및 반환
        boardColumns = columnRepository.saveAll(boardColumns);
        return boardColumns.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // DTO 변환 메서드
    // BoardColumn 엔티티를 ColumnDto 로 변환하는 메서드
    // 엔티티 속성을 DTO에 복사
    private ColumnDto convertToDto(BoardColumn boardColumn) {
        ColumnDto dto = new ColumnDto();
        dto.setId(boardColumn.getId());
        dto.setName(boardColumn.getName());
        dto.setOrder(boardColumn.getOrder());
        return dto;
    }
}