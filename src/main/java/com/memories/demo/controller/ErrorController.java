package com.memories.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    
    @GetMapping("/error")
    @ResponseBody
    public String handleError() {
        return "An error occurred. Please try again later.";
    }
}
