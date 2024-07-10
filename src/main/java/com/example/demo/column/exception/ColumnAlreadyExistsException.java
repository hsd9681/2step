package com.example.demo.column.exception;

// 이미 존재하는 컬럼을 생성하려는 경우
public class ColumnAlreadyExistsException extends RuntimeException {
    public ColumnAlreadyExistsException(String message) {
        super(message);
    }
}