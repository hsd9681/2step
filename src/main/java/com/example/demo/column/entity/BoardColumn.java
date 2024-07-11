package com.example.demo.column.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "columns")
@Getter
@Setter

// DB 매핑, 저장, 검색 & 엔티티 관리를 위한 BoardColumn 클래스

public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    // 값이 유일 & null 허용 X
    @Column(unique = true, nullable = false)
    private String name; // 상태 이름

    @Column(nullable = false)
    private Integer order; // 컬럼의 순서
}