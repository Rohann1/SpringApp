package com.memories.demo;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home(Model model, Principal principal) {
	    String username = principal.getName(); // Get the logged-in user's username
	    model.addAttribute("username", username);
	    return "home";
	}
}