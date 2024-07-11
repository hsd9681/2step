package com.example.demo.common.exception;


import com.example.demo.common.exception.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> defaultException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .msg(ErrorCode.FAIL.getMsg())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(HttpServletRequest request, CustomException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .msg(e.getErrorCode().getMsg())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(exceptionResponse, e.getErrorCode().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> processValidationError(HttpServletRequest request, MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder builder = new StringBuilder();
        String msg = ErrorCode.FAIL.getMsg();

        if (bindingResult.hasFieldErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            String fieldName = fieldError.getField();

            builder.append("[");
            builder.append(fieldName);
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" / 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");

            msg = builder.toString();
        }

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .msg(msg)
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}