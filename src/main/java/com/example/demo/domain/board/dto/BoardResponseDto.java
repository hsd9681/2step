package com.example.demo.domain.board.dto;

import com.example.demo.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;

    @Builder
    public BoardResponseDto (Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getBoardName();
        this.content = board.getIntro();
    }
}

