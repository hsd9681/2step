package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.BoardRequestDto;
import com.example.demo.domain.board.dto.BoardResponseDto;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("보드 생성 /성공")
    void test1() {
        BoardRequestDto requestDto = new BoardRequestDto("Title", "Content");
        Board board = new Board(requestDto.getTitle(), requestDto.getContent());

        when(boardRepository.save(any(Board.class))).thenReturn(board);

        BoardResponseDto responseDto = boardService.createBoard(requestDto);

        assertEquals(board.getBoardName(), responseDto.getTitle());
        assertEquals(board.getIntro(), responseDto.getContent());
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    @DisplayName("보드 조회")
    void test2() {
        Board board1 = new Board("title1", "content1");
        Board board2 = new Board("title2", "content2");
        //
    }

    @Test
    @DisplayName("보드 수정 /성공")
    void test3() {
        Board board = new Board("title", "content");
        BoardRequestDto boardRequestDto = new BoardRequestDto("update title", "update content");

        when(boardRepository.getBoardById(1L)).thenReturn(board);

        BoardResponseDto boardResponseDto = boardService.updateBoard(1L, boardRequestDto);

        assertEquals(boardRequestDto.getTitle(), boardResponseDto.getTitle());
        assertEquals(boardRequestDto.getContent(), boardResponseDto.getContent());
        verify(boardRepository, times(1)).getBoardById(1L);
    }

    @Test
    @DisplayName("보드 삭제")
    void test4() {
        doNothing().when(boardRepository).deleteById(1L);
        //
    }


}