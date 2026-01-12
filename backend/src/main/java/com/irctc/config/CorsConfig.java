package com.irctc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // API endpoints - restricted to frontend origins
        registry.addMapping("/api/**")
                .allowedOrigins(
                    "http://localhost:5173", 
                    "http://localhost:3000",
                    "https://ticketbookingsystem-nu.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
        
        // Health endpoint - allow all origins for monitoring services
        registry.addMapping("/health")
                .allowedOrigins("*")
                .allowedMethods("GET");
        
        registry.addMapping("/")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }
}
