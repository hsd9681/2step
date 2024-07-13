package com.example.demo.domain.permission.entity;

import lombok.Getter;

@Getter
public enum PermissionType {
    MANAGER(UserPermissionType.MANAGER),
    USER(UserPermissionType.USER);

    private final String authority;

    PermissionType(String authority) {
        this.authority = authority;
    }

    public static class UserPermissionType {
        public static final String MANAGER = "MANAGER";
        public static final String USER = "USER";
    }
}
