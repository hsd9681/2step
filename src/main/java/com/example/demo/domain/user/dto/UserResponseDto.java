package com.example.demo.domain.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;

    public UserResponseDto(String username) {
        this.username = username;
    }
}
