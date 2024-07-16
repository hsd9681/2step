package com.example.demo.domain.card.dto;

import com.example.demo.domain.card.entity.Card;
import lombok.Getter;


@Getter
public class CardResponseDto {
    private Long id;
    private String title;
    private String status;
    private String content;
    private String deadline;
    private String worker;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.status = card.getStatus();
        this.content = card.getContent();
        this.deadline = card.getDeadline();
        this.worker = card.getWorker();
    }
}