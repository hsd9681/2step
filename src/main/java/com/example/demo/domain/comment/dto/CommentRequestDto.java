package com.example.demo.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    // 필수 데이터인 내용
    @NotBlank(message = "댓글 내용이 필요합니다.")
    @Size(min = 1, max = 255, message = "댓글 내용은 1자 이상 255자 이하여야 합니다.")
    private String content;
}


// 클라이언트에서 요청을 보낼 때 사용할 수 있는 예시 (JSON 형태)
/*
{
    "content": "comment"
}
*/

// 응답 예시 (JSON 형태)
/*
{
    "id": 1,
    "content": "comment",
    "createdAt": "2023-07-15 ~ ",
    "username": "123"
}
*/