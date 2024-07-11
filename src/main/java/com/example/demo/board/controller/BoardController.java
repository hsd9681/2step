package com.example.demo.board.controller;

import com.example.demo.board.dto.BoardRequestDto;
import com.example.demo.board.dto.BoardResponseDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    // 보드 생성
    @PostMapping
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        Board board = boardService.createBoard(requestDto, userDetails.getUsername());
        return new BoardResponseDto(board.getId(), board.getBoardName(), board.getIntro());
    }

    // 보드 리스트 조회
    @GetMapping
    public List<BoardResponseDto> getBoard(@AuthenticationPrincipal UserDetails userDetails) {
        List<BoardResponseDto> boardList = boardService.getBoard(userDetails.getUsername());
        return boardList;
    }

    // 보드 수정
    @PutMapping("/{board_id}")
    public BoardResponseDto updateBoard(@PathVariable Long board_id,
                                        @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(board_id, requestDto);
    }

    // 보드 삭제
    @DeleteMapping("/{board_id}")
    public void deleteBoard(@PathVariable Long board_id,
                            @AuthenticationPrincipal UserDetails userDetails) {
        boardService.deleteBoard(board_id, userDetails.getUsername());
    }

    // 보드 초대
    @PostMapping("/{board_id}/invite")
    public void invite(@PathVariable Long board_id,
                       @AuthenticationPrincipal UserDetails userDetails,
                       @RequestBody String username) {
        boardService.invite(board_id, userDetails.getUsername(), username);
    }


}