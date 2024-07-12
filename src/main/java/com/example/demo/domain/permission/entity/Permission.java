package com.example.demo.domain.permission.entity;

import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;

@Entity @Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board board;

    @Column(name = "authority")
    private PermissionType authority;
}
