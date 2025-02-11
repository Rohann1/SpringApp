package com.memories.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    // Show login page
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "login"; // Displays login.html
//    }

    // Handle registration
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String password, 
                               Model model) {
        // Check if user already exists
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already taken!");
            return "login"; // Stay on login page with error message
        }

        // Create new user and save it
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Hash password
        userRepository.save(newUser);

        model.addAttribute("success", "Registration successful! Please log in.");
        return "login"; // Redirect back to login with success message
    }
}
