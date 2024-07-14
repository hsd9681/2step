package com.example.demo.domain.column.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestColumnDto {
    @NotBlank(message = "상태 이름은 필수입니다.")
    @Size(min = 1, max = 50, message = "상태 이름은 1자 이상 50자 이하여야 합니다.")
    private final String name;
}