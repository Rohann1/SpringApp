	package com.memories.demo.service;

import org.springframework.stereotype.Service;

import com.memories.demo.model.Letter;
import com.memories.demo.model.User;
import com.memories.demo.repository.LetterRepository;
import com.memories.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class LetterService {
    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    public LetterService(LetterRepository letterRepository, UserRepository userRepository) {
        this.letterRepository = letterRepository;
        this.userRepository = userRepository;
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

    public void createDummyLetters() {
        User dummyUser = userRepository.findByUsername("DummyUser");

        if (dummyUser != null && letterRepository.findByUser(dummyUser).isEmpty()) {
            List<Letter> dummyLetters = List.of(
                createLetter("I have finally completed my Operating System Course. Towards DBMS now!", 
                    LocalDateTime.of(2025, 1, 05, 10, 17), dummyUser),
                    
                    createLetter("My dog just got his vaccine and he is now doing better. I hope he recovers soon.", 
                    LocalDateTime.of(2025, 1, 15, 19, 43), dummyUser),
                    
                    createLetter("DBMS course completed. I can finally move on to interviews!", 
                    LocalDateTime.of(2025, 1, 24, 01, 32), dummyUser)
            );

            letterRepository.saveAll(dummyLetters);
        }
    }

    private Letter createLetter(String content, LocalDateTime timestamp, User user) {
        Letter letter = new Letter();
        letter.setContent(content);
        letter.setCreatedAt(timestamp);
        letter.setUser(user);
        return letter;
    }
}