package com.cinetrack.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cinetrack.model.Perfil;
import com.cinetrack.model.Usuario;
import com.cinetrack.service.PerfilService;
import com.cinetrack.service.UsuarioService;

@Configuration
public class DatabaseSeederConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeederConfig.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UsuarioService usuarioService, PerfilService perfilService) {
        return args -> {
            log.info("--- INICIANDO SEED DE DATOS (ATLAS) ---");

            var existente = usuarioService.buscarPorEmail("admin@cinetrack.com");
            if (existente.isEmpty()) {
                Usuario admin = usuarioService.registrar("admin@cinetrack.com", "admin123", "PREMIUM");
                admin.setRol("ADMIN");
                usuarioService.guardar(admin);

                Perfil perfil = perfilService.crearPerfil(admin, "Admin", "avatar1");
                log.info("✓ Usuario admin creado: {} con perfil '{}'", admin.getEmail(), perfil.getNombre());
            } else {
                // Garantizar que el admin siempre tiene la contraseña correcta y está activo
                Usuario admin = existente.get();
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol("ADMIN");
                admin.setActivo(true);
                usuarioService.guardar(admin);
                log.info("✓ Usuario admin verificado y contraseña sincronizada.");
            }

            log.info("--- SEED FINALIZADO ---");
        };
    }
}
