package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.dto.BoardResponseDto;
import com.example.demo.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Board getBoardById(Long boardId);

}
