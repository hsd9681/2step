package com.example.demo.domain.permission.entity;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permission")
@NoArgsConstructor
@Getter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Getter
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private PermissionType authority;

    public Permission(User user, Board board, PermissionType permissionType) {
        this.user = user;
        this.board = board;
        this.authority = permissionType;
    }
}
