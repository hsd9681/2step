package com.example.demo.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private final String title;
    private final String content;

    public BoardRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}