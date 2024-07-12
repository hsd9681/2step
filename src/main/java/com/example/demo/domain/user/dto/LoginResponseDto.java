package com.example.demo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private int httpStatusCode;
    private String message;
}
