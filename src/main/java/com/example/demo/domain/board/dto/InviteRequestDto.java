package com.example.demo.domain.board.dto;

import lombok.Getter;

@Getter
public class InviteRequestDto {
    private final String username;

    public InviteRequestDto(String username) {
        this.username = username;
    }
}