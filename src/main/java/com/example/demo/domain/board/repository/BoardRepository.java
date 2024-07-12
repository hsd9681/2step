package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.dto.BoardResponseDto;
import com.example.demo.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board getBoardById(Long boardId);

    List<BoardResponseDto> findAllByUserName(String username);
}
