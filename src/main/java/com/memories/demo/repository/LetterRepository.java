package com.memories.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memories.demo.model.Letter;
import com.memories.demo.model.User;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    // Find all letters by a specific user
    List<Letter> findByUser(User user);
}
