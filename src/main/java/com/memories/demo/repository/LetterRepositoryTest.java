package com.memories.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.memories.demo.model.Letter;
import com.memories.demo.model.User;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class LetterRepositoryTest {

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindLettersByUser() {
        // Create a test user
        User user = new User("testuser", "password");
        userRepository.save(user);

        // Create a test letter
        Letter letter = new Letter("This is a test letter.", LocalDateTime.now(), user);
        letterRepository.save(letter);

        // Retrieve letters by user
        List<Letter> letters = letterRepository.findByUser(user);
        System.out.println("Letters found: " + letters.size());
        letters.forEach(l -> System.out.println(l.getContent()));
    }
}
