package com.example.demo.domain.board;

import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.board.dto.BoardRequestDto;
import com.example.demo.domain.board.dto.BoardResponseDto;
import com.example.demo.domain.board.dto.InviteRequestDto;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.permission.entity.Permission;
import com.example.demo.domain.permission.entity.PermissionType;
import com.example.demo.domain.permission.PermissionRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final PermissionRepository permissionRepository;

    // 보드 생성
    public BoardResponseDto createBoard(BoardRequestDto requestDto, String username) {
        User user = userService.findUserByUsername(username); // 보드를 만드는 사용자

        Board board = new Board(requestDto.getTitle(), requestDto.getContent()); //보드 생성
        board.setManager(user); // 사용자를 매니저로

        boardRepository.save(board); // 보드를 저장 => entity 연관관계가 맺어져있어서(cascade.all) permission도 저장됨
        return new BoardResponseDto(board);
    }

    // 보드 조회
    public List<BoardResponseDto> getBoard(String username) {
        // username을 가진 board 전체 조회 : 일반유저와 매니저 모두 조회 가능 - 본인이 속한 모든 보드 조회하기
        User user = userService.findUserByUsername(username);
        Long userid = user.getId();

        List<Board> boards = boardRepository.findByPermissions_User_Id(userid);
        return boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // 보드 수정
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, String username) {
        // 매니저만 수정 가능
        User user = userService.findUserByUsername(username);
        Long userid = user.getId();

        Permission permission = permissionRepository.findByUser_IdAndBoard_Id(userid, boardId);

        if (permission.getAuthority() == PermissionType.MANAGER) { // user = manager 인 경우
            Board board = boardRepository.getBoardById(boardId);

            board.update(requestDto.getTitle(), requestDto.getContent());
            return new BoardResponseDto(board.getId(), board.getBoardName(), board.getIntro());

        } else {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }
    }

    // 보드 삭제
    public void deleteBoard(Long boardId, String username) {
        // 매니저만 삭제 가능
        // 확인 메세지 출력 후 확인 버튼 누르면 삭제 되도록 단계 거치기
        User user = userService.findUserByUsername(username);
        Long userid = user.getId();

        Permission permission = permissionRepository.findByUser_IdAndBoard_Id(userid, boardId);

        if (permission.getAuthority() == PermissionType.MANAGER) {
            boardRepository.deleteById(boardId);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }
    }

    // 사용자 초대
    public void invite(Long boardId, String username, InviteRequestDto requestDto) {
        Board board = boardRepository.getBoardById(boardId);
        User user = userService.findUserByUsername(username); // 초대하는 사용자 : 권한이 매니저인지 확인
        Long userid = user.getId();

        String invitedUsername = requestDto.getUsername(); // 초대 할 사용자
        User invitedUser = userService.findUserByUsername(invitedUsername);

        Permission permission = permissionRepository.findByUser_IdAndBoard_Id(userid, boardId);

        if (permission.getAuthority() == PermissionType.MANAGER) {
            board.setUser(invitedUser);
            boardRepository.save(board);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }
    }

    public Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 보드를 찾을 수 없습니다: " + boardId));
    }
}
