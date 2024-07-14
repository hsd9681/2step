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
    @Column(unique = true, nullable = false)
    private String name; // 상태 이름

    @Column(nullable = false)
    private Long order; // 컬럼의 순서

    // Board(1) : Column(N)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    // Column(1) : Card(N)
    @OneToMany(mappedBy = "boardcolumn", cascade = CascadeType.ALL)
    private List<Card> cards;

    // 생성자 추가
    public BoardColumn(String name, Long order, Board board) {
        this.name = name;
        this.order = order;
        this.board = board;
    }

    // 순서 변경 메서드
    public void changeOrder(Long newOrder) {
        if (newOrder == null || newOrder < 1) {
            throw new CustomException(ErrorCode.ORDER_MUST_BE_POSITIVE);
        }
        this.order = newOrder;
    }
}