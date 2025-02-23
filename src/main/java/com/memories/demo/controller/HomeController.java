package com.memories.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;
import java.util.Map; // Import Map

import com.memories.demo.model.Letter;
import com.memories.demo.model.User;
import com.memories.demo.repository.UserRepository;
import com.memories.demo.repository.LetterRepository;
import com.memories.demo.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody; // Import ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LetterService letterService;

    @Autowired
    private LetterRepository letterRepository;

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        
        List<Letter> letters = new ArrayList<>();  // Default empty list
        
        // Only fetch and show letters for DummyUser
        if ("DummyUser".equals(username)) {
            letters = letterService.getLettersByUser(user);
        }

        model.addAttribute("username", username);
        model.addAttribute("letters", letters);
        
        return "home";
    }

    @PostMapping("/letters")
    public String saveLetter(@RequestParam String content, RedirectAttributes redirectAttributes) {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        // Create and save the letter using LetterService
        letterService.createLetter(content, user);

        // Add success message as a flash attribute
        redirectAttributes.addFlashAttribute("successMessage", "Letter saved successfully!");

        // Redirect back to the homepage to display updated letters and message
        return "redirect:/home";
    }
}