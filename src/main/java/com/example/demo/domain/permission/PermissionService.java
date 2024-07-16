package com.example.demo.domain.permission;

import com.example.demo.domain.permission.entity.Permission;
import com.example.demo.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<UserResponseDto> getUsersByBoard(Long boardId) {
        List<Permission> permissions = permissionRepository.findByBoardId(boardId);

        List<UserResponseDto> users = permissions.stream()
                .map(permission -> new UserResponseDto(permission.getUser().getUsername()))
                .collect(Collectors.toList());

        return users;
    }
}
