package com.memories.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "logout", required = false) String logout, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/home"; // If user is logged in, send them to home page
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out.");
        }

        return "login"; // Show login page
    }
}