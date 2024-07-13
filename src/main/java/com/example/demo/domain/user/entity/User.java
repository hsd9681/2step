package com.example.demo.domain.user.entity;

import com.example.demo.domain.permission.entity.Permission;
import com.example.demo.domain.permission.entity.PermissionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "users")
@NoArgsConstructor @Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String refreshToken;

    // User(1) : Permission(N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Permission> permissions = new ArrayList<>();

    @Builder
    public User(String username, String password, String refreshToken) {
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 사용자의 권한을 반환하는 역할
    public PermissionType getPermission() {
        // permissions 가 null 이거나 리스타가 비어 있는 경우 USER 권한 반환
        // null => 리스트가 초기화되지 않았거나 객체가 할당되지 않은 상태를 체크
        // isEmpty() => permissions 리스트가 초기화되었지만 요소가 포함되지 않은 상태를 체크
        if (permissions == null || permissions.isEmpty()) {
            return PermissionType.USER; // 기본 권한
        }

        // MANAGER 권한이 있는지 확인
        boolean hasManagerPermission = permissions.stream()
                .anyMatch(permission -> PermissionType.MANAGER.equals(permission.getAuthority()));

        // 사용자가 MANAGER 권한을 가질 시 MANAGER 반환, 아니면 USER 반환
        return hasManagerPermission ? PermissionType.MANAGER : PermissionType.USER;
    }
}
