package com.example.demo.domain.card.entity;

import com.example.demo.domain.card.dto.CardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name="content", nullable = true)
    private  String content;

    @Column(name="deadline", nullable = true)
    private String deadline;

    @Column(name="worker", nullable = true)
    private  String worker;

    public Card(CardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.status = requestDto.getStatus();
        this.content = requestDto.getContent();
        this.deadline = requestDto.getDeadline();
        this.worker = requestDto.getWorker();
    }
    public void update(CardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.deadline = requestDto.getDeadline();
        this.worker = requestDto.getWorker();
    }
}