package com.example.demo.column.repository;

import com.example.demo.column.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// JPA로 DB 에 접근하기 위한 ColumnRepository 인터페이스
// BoardColumn 엔티티에 대한 DB 엑세스, CRUD, 커스텀 쿼리(EX.findByName) 지원
public interface ColumnRepository extends JpaRepository<BoardColumn, Long> {
    // 특정 컬럼 '상태 이름'으로 찾기 (현재는 사용 X, 조건에 특정 컬럼 조회 X)
    // 향후 사용가능성을 보고 확장 OR 삭제
    Optional<BoardColumn> findByName(String name);
    boolean existsByName(String name); // 이미 존재하는 '상태 이름' 을 확인하기 위한 메서드
}