package com.example.demo.domain.board.controller;

import com.example.demo.domain.board.dto.BoardRequestDto;
import com.example.demo.domain.board.dto.BoardResponseDto;
import com.example.demo.domain.board.service.BoardService;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    // 보드 생성
    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(requestDto, userDetails.getUsername()));
    }

    // 보드 리스트 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoard(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoard(userDetails.getUsername()));
    }

    // 보드 수정
    @PutMapping("/{board_id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId,
                                                        @RequestBody BoardRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(boardId, requestDto, userDetails.getUsername()));
    }

    // 보드 삭제
    @DeleteMapping("/{board_id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetails userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("보드가 삭제되었습니다.");
    }

    // 보드 초대
    @PostMapping("/{board_id}/invite")
    public ResponseEntity<String> invite(@PathVariable Long boardId, @AuthenticationPrincipal UserDetails userDetails, @RequestBody String username) {
        boardService.invite(boardId, userDetails.getUsername(), username);
        return ResponseEntity.status(HttpStatus.OK).body("사용자가 추가되었습니다.");
    }


}