package com.memories.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Handle registration
import org.springframework.transaction.annotation.Transactional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String registerUser(
        @RequestParam String username, 
        @RequestParam String password, 
        RedirectAttributes redirectAttributes
    ) {
        System.out.println("🔹 REGISTER ENDPOINT HIT!");
        System.out.println("🔹 Received username: " + username);

        // Generate a unique username for testing (if the username starts with "testuser")
        if (username.startsWith("qwe")) {
            username = username + "_" + System.currentTimeMillis(); // Append timestamp
        }

        // Check if username exists
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "Username already taken");
            redirectAttributes.addFlashAttribute("register", true); // Reopen modal for error
            return "redirect:/login";
        }

        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));

            System.out.println("🛠 Attempting to save user...");
            userRepository.save(newUser);
            System.out.println("✅ User saved successfully!");

            redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
            // Do NOT set the register flag for success
        } catch (Exception e) {
            System.out.println("❌ Error while saving user: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            redirectAttributes.addFlashAttribute("register", true); // Reopen modal for error
        }

        return "redirect:/login";
    }
}
