package com.memories.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memories.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
