package com.example.demo.column.exception;

// 존재하지 않는 컬럼을 조작하려는 경우
public class ColumnNotFoundException extends RuntimeException {
    public ColumnNotFoundException(String message) {
        super(message);
    }
}