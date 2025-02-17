package com.memories.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    // Find all letters by a specific user
    List<Letter> findByUser(User user);
}
