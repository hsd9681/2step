package com.example.demo.column.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "columns")
@Getter
@NoArgsConstructor
// DB 매핑, 저장, 검색 & 엔티티 관리를 위한 BoardColumn 클래스

public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    // 값이 유일 & null 허용 X
    @Column(unique = true, nullable = false)
    private String name; // 상태 이름

    @Column(nullable = false)
    private Long order; // 컬럼의 순서

    // 생성자 추가
    public BoardColumn(String name, Long order) {
        this.name = name;
        this.order = order;
    }

    // 순서 변경 메서드
    public void changeOrder(Long newOrder) {
        if (newOrder == null || newOrder < 1) {
            throw new IllegalArgumentException("Order must be a positive number");
        }
        this.order = newOrder;
    }

    // 📢 임시 엔티티 관계 설정
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board board;
//
//    @OneToMany(mappedBy = "boardcolumn", cascade = CascadeType.ALL)
//    private List<Card> cards;
}