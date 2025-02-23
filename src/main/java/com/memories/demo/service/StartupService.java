package com.memories.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StartupService {

    @Autowired
    private LetterService letterService;

    @PostConstruct
    public void init() {
        letterService.createDummyLetters();
    }
}