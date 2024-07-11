package com.example.demo.column.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


// 데이터 전송, 유효성 검증, 데이터 매핑을 위한 ColumnoDto 클래스
@Getter
@Setter
public class ColumnDto {
    private Long id; // 고유 식별자

    @NotBlank(message = "상태 이름은 필수입니다.")
    @Size(min = 1, max = 50, message = "상태 이름은 1자 이상 50자 이하여야 합니다.")
    private String name; // 상태 이름

    private Integer order; // 순서
}