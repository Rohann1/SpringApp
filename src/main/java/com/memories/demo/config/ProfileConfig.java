package com.memories.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(ProfileConfig.class);
    
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        String activeProfiles = String.join(", ", event.getApplicationContext().getEnvironment().getActiveProfiles());
        logger.info("Currently active profile(s): {}", activeProfiles);
        System.out.println("Currently active profile(s): " + activeProfiles);  // Added for immediate console output
    }
} 