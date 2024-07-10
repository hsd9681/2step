package com.example.demo.column.exception;

// 권한이 없는 경우
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}