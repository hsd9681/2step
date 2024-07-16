package com.example.demo.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;

    public BoardRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}