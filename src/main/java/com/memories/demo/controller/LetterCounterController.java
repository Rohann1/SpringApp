package com.memories.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.security.Principal; // Import Principal
import com.memories.demo.model.User; // Import User
import com.memories.demo.repository.UserRepository; // Import UserRepository
import com.memories.demo.service.LetterService; // Import LetterService
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired

@RestController // Keep RestController annotation
public class LetterCounterController {

    @Autowired // Inject UserRepository
    private UserRepository userRepository;

    @Autowired // Inject LetterService
    private LetterService letterService;

    @GetMapping("/api/letter_count")
    public Map<String, Integer> getLetterCount(Principal principal) { // Add Principal parameter
        String username = principal.getName(); // Get username from Principal
        User user = userRepository.findByUsername(username); // Get User entity
        int letterCount = letterService.getLettersByUser(user).size(); // Get user-specific letter count
        return Map.of("count", letterCount); // Return user-specific count
    }
}