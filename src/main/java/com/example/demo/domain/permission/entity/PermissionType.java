package com.example.demo.domain.permission.entity;

public enum PermissionType {
    MANAGER(UserPermissionType.MANAGER),
    USER(UserPermissionType.USER);

    private final String authority;

    PermissionType(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class UserPermissionType {
        public static final String MANAGER = "MANAGER";
        public static final String USER = "USER";
    }
}
