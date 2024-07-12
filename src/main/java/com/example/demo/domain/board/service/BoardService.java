package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.BoardRequestDto;
import com.example.demo.domain.board.dto.BoardResponseDto;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.permission.entity.Permission;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

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
//        List<BoardResponseDto> boards = boardRepository.findAllByUserName(username);
        User user = userService.findUserByUsername(username);
        Long userid = user.getId();
        List<BoardResponseDto> boards = boardRepository.findAllByUserId(userid);
        return boards;
    }

    // 보드 수정
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, String username) {
        // 매니저만 수정 가능
        User user = userService.findUserByUsername(username);

        Board board = boardRepository.getBoardById(boardId);
        board.update(requestDto.getTitle(), requestDto.getContent());
        return new BoardResponseDto(board.getId(), board.getBoardName(), board.getIntro());
    }

    // 보드 삭제
    public void deleteBoard(Long boardId, String username) {
        // 매니저만 삭제 가능
        // 확인 메세지 출력 후 확인 버튼 누르면 삭제 되도록 단계 거치기
        User user = userService.findUserByUsername(username);

        boardRepository.deleteById(boardId);
    }

    // 사용자 초대
    public void invite(Long boardId, String username, String invitedUsername) {
        Board board = boardRepository.getBoardById(boardId);
        User user = userService.findUserByUsername(username); // 초대하는 사용자 : 권한이 매니저인지 확인
        User invitedUser = userService.findUserByUsername(invitedUsername); // 초대 할 사용자

//        Permission permission
    }
}
