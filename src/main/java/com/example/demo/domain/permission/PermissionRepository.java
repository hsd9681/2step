package com.example.demo.domain.permission;

import com.example.demo.domain.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByUser_IdAndBoard_Id(Long userid, Long boardId);

    List<Permission> findByBoardId(Long boardId);
}
