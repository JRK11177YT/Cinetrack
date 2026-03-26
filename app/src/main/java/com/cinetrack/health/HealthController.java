package com.cinetrack.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("status", "UP", "service", "cinetrack-app");
    }

    @GetMapping("/db")
    public ResponseEntity<Map<String, String>> db() {
        try {
            String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
            return ResponseEntity.ok(Map.of(
                "status", "UP",
                "database", dbName == null ? "unknown" : dbName
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "status", "DOWN",
                "error", ex.getMessage()
            ));
        }
    }
}

