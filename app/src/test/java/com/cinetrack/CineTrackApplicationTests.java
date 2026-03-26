package com.cinetrack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CineTrackApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring arranca en entorno de test.
        assertNotNull(jdbcTemplate);
    }

    @Test
    void shouldConnectToCinetrackDatabase() {
        String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
        assertEquals("cinetrack_db", dbName);
    }
}
