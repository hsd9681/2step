package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}