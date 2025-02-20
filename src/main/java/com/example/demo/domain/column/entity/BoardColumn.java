package com.example.demo.domain.column.entity;

import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Column(nullable = false)
    private String name; // 상태 이름

    @Column(nullable = false)
    private Long orders; // 컬럼의 순서

    // Board(1) : Column(N)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    // Column(1) : Card(N)
    @OneToMany(mappedBy = "boardColumn", cascade = CascadeType.ALL)
    private List<Card> cards;

    // 생성자 추가
    public BoardColumn(String name, Long orders, Board board) {
        this.name = name;
        this.orders = orders;
        this.board = board;
    }

    // 순서 변경 메서드
    public void changeOrders(Long newOrders) {
        if (newOrders == null || newOrders < 1) {
            throw new CustomException(ErrorCode.ORDER_MUST_BE_POSITIVE);
        }
        this.orders = newOrders;
    }
}