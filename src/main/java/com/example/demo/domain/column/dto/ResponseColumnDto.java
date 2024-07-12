package com.example.demo.domain.column.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseColumnDto {
    private final Long id;
    private final String name;
    private final Long order;
}