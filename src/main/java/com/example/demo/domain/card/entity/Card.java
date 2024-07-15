package com.example.demo.domain.card.entity;

import com.example.demo.domain.card.dto.CardRequestDto;
import com.example.demo.domain.column.entity.BoardColumn;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "deadline", nullable = true)
    private String deadline;

    @Column(name = "worker", nullable = true)
    private String worker;

    @Setter
    @Getter
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "boardcolumn_id", nullable = false)
    private BoardColumn boardColumn;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Card(CardRequestDto requestDto, String username, BoardColumn boardColumn, User user) {
        this.title = requestDto.getTitle();
        this.status = boardColumn.getName();
        this.content = requestDto.getContent();
        this.deadline = requestDto.getDeadline();
        this.worker = username;
        this.boardColumn = boardColumn;
        this.user = user;
    }

    public void update(CardRequestDto requestDto, String username, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.deadline = requestDto.getDeadline();
        this.worker = username;
        this.user = user;
    }

    public String getStatus(){
        return this.boardColumn.getName();
    }
}