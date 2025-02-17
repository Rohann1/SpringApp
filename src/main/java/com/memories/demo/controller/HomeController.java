package com.memories.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime; // Import LocalDateTime
import com.memories.demo.model.Letter; // Import Letter
import com.memories.demo.model.User;
import com.memories.demo.repository.UserRepository;
import com.memories.demo.repository.LetterRepository; // Import LetterRepository
import com.memories.demo.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Import PostMapping
import org.springframework.web.bind.annotation.RequestParam; // Import RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LetterService letterService;

    @Autowired // Inject LetterRepository
    private LetterRepository letterRepository;

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("letters", letterService.getLettersByUser(user));
        model.addAttribute("canViewLetters", letterService.canViewLetterContent());

        return "home";
    }

    @PostMapping("/letters")
    public String saveLetter(@RequestParam String content, RedirectAttributes redirectAttributes) { // Add RedirectAttributes
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        // Create and save the letter using LetterService
        letterService.createLetter(content, user);

        // Add success message as a flash attribute
        redirectAttributes.addFlashAttribute("successMessage", "Letter saved successfully!"); // Added success message

        // Redirect back to the homepage to display updated letters and message
        return "redirect:/home";
    }
}