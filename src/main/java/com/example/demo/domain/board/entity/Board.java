package com.example.demo.domain.board.entity;

import com.example.demo.domain.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    // 보드 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 보드 이름
    @Column(nullable = false)
    private String boardName;

    // 한 줄 설명
    @Column
    private String intro;


    // constructor
    @Builder
    public Board (String boardName, String intro) {
        this.boardName = boardName;
        this.intro = intro;
    }

    public Board(BoardRequestDto requestDto) {
        this.boardName = requestDto.getTitle();
        this.intro = requestDto.getContent();
    }

    // 보드 수정 메서드
    public void update(String title, String content) {
        this.boardName = title;
        this.intro = content;
    }
}
