package com.example.demo.board.dto;

import com.example.demo.board.entity.Board;
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