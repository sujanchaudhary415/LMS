package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.enums.AppRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmailIgnoreCase(String email);
    Page<User> findByRolesContaining(AppRole role, Pageable pageable);
    Optional<User> findByEmailIgnoreCase(String email);
}
