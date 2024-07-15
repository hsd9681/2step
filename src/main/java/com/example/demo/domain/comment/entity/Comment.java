package com.example.demo.domain.comment.entity;

import com.example.demo.domain.card.entity.Card;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Comment(N) : Card(1)
    // 댓글은 반드시 카드에 속해야 함을 의미
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card; // 댓글이 속한 카드

    // Comment(N) : User(1)
    // 댓글은 반드시 사용자에 속해야함을 의미
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 댓글을 작성한 사용자

    // 생성자
    public Comment(String content, Card card, User user) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.card = card;
        this.user = user;
    }
}