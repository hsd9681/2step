package com.example.demo.domain.board.entity;

import com.example.demo.domain.board.dto.BoardRequestDto;
import com.example.demo.domain.permission.entity.Permission;
import com.example.demo.domain.permission.entity.PermissionType;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    // permission
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Permission> permissions = new ArrayList<>();

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

    // 보드 생성 시 : 유저를 매니저로 설정하는 메서드
    public void setManager (User user) {
        this.permissions.add(new Permission(user, this, PermissionType.MANAGER));
    }
}
