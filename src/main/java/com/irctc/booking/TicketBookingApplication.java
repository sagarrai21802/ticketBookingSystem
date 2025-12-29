package com.irctc.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

/**
 * Indian Train Ticket Booking System
 * Main Spring Boot Application Entry Point
 * 
 * Similar to IRCTC - Handles train search, booking, and ticket management
 * 
 * @author Your Name
 * @version 1.0.0
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Indian Train Ticket Booking API",
        version = "1.0.0",
        description = "REST API for Indian Railway Ticket Booking System - Similar to IRCTC",
        contact = @Contact(
            name = "Support Team",
            email = "support@irctc-clone.com"
        )
    )
)
public class TicketBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketBookingApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("🚂 INDIAN TRAIN TICKET BOOKING SYSTEM");
        System.out.println("========================================");
        System.out.println("Server started at: http://localhost:8080");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
