	package com.memories.demo.service;

import org.springframework.stereotype.Service;

import com.memories.demo.model.Letter;
import com.memories.demo.model.User;
import com.memories.demo.repository.LetterRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class LetterService {
    private final LetterRepository letterRepository;

    public LetterService(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    public List<Letter> getLettersByUser(User user) {
        return letterRepository.findByUser(user);
    }

    public Letter getLetterById(Long id, User user) {
        return letterRepository.findById(id)
            .filter(letter -> letter.getUser().getId().equals(user.getId()))
            .orElse(null);
    }

    public Letter createLetter(String content, User user) {
        Letter letter = new Letter();
        letter.setContent(content);
        letter.setCreatedAt(LocalDateTime.now());
        letter.setUser(user);
        return letterRepository.save(letter);
    }

    public boolean canViewLetterContent() {
        LocalDateTime now = LocalDateTime.now();
        // Allow viewing from December 25th to January 5th
        return (now.getMonth() == Month.DECEMBER && now.getDayOfMonth() >= 25) ||
               (now.getMonth() == Month.JANUARY && now.getDayOfMonth() <= 5);
    }
}