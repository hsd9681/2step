package com.example.demo.domain.permission.repository;

import com.example.demo.domain.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByUser_IdAndBoard_Id(Long userid, Long boardId);
}
