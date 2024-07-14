package com.example.demo.domain.card.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String status;
    private String content;
    private String deadline;
}