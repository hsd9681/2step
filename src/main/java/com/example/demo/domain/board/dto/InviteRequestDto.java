package com.example.demo.domain.board.dto;

import lombok.Getter;

@Getter
public class InviteRequestDto {
    private String username;

    public InviteRequestDto() {
    }

    public InviteRequestDto(String username) {
        this.username = username;
    }
}
