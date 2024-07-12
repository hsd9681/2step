package com.example.demo.domain.column.repository;

import com.example.demo.domain.column.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


// JPA로 DB 에 접근하기 위한 ColumnRepository 인터페이스
// BoardColumn 엔티티에 대한 DB 엑세스, CRUD, 커스텀 쿼리(EX.findByName) 지원
public interface ColumnRepository extends JpaRepository<BoardColumn, Long> {

    boolean existsByName(String name); // 이미 존재하는 '상태 이름' 을 확인하기 위한 메서드

    // ColumnService 클래스에서 현재 최대 순서 값을 조회한 후 1을 더하는 방법을 사용하기 위해 메소드 추가
    @Query("SELECT MAX(c.order) FROM BoardColumn c") // BoardColumn 엔티티의 order 속성 값 중 최대 값 선택
    Optional<Long> findMaxOrder(); // 결과가 없을 수도 있기에 Optional을 사용하여 안전하게 처리
}