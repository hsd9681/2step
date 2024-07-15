package com.example.demo.domain.column.repository;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.column.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


// JPA로 DB 에 접근하기 위한 ColumnRepository 인터페이스
// BoardColumn 엔티티에 대한 DB 엑세스, CRUD, 커스텀 쿼리(EX.findByName) 지원
public interface ColumnRepository extends JpaRepository<BoardColumn, Long> {

    // 특정 보드의 특정 컬럼을 조회
    Optional<Object> findByIdAndBoard(Long columnId, Board board);

    // 특정 보드 내에서 컬림 이름의 중복을 확인하는 메서드
    boolean existsByNameAndBoard(String name, Board board);
    // 특정 보드 내에서 최대 순서 값을 찾음
    // 새 컬럼 추가 시 사용할 메서드
    @Query("SELECT MAX(c.orders) FROM BoardColumn c WHERE c.board = :board")
    Optional<Long> findMaxOrdersByBoard(@Param("board") Board board);

    // 특정 보드의 모든 컬럼을 순서대로 조회
    @Query("SELECT c FROM BoardColumn c WHERE c.board = :board ORDER BY c.orders")
    List<BoardColumn> findAllByBoardOrdersByOrder(@Param("board") Board board);

    Optional<BoardColumn> findByName(String name);

    List<BoardColumn> findAllByBoard(Board board);
}