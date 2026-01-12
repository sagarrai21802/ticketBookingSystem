package com.irctc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * Health check endpoint for UptimeRobot keep-alive pings.
 * This endpoint is lightweight and fast to respond.
 */
@RestController
public class HealthController {

    private final Instant startTime = Instant.now();

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "timestamp", Instant.now().toString(),
            "uptime", java.time.Duration.between(startTime, Instant.now()).toSeconds() + "s"
        ));
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        return ResponseEntity.ok(Map.of(
            "message", "IRCTC Backend API",
            "status", "running"
        ));
    }
}
