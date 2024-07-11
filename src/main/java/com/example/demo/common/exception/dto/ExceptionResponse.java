package com.example.demo.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ExceptionResponse {
    String msg;
    String path;
}