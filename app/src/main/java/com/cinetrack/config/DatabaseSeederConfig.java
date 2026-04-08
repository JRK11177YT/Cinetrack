package com.cinetrack.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cinetrack.model.Perfil;
import com.cinetrack.model.Usuario;
import com.cinetrack.service.PerfilService;
import com.cinetrack.service.UsuarioService;

@Configuration
public class DatabaseSeederConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeederConfig.class);

    @Bean
    CommandLineRunner initDatabase(UsuarioService usuarioService, PerfilService perfilService) {
        return args -> {
            log.info("--- INICIANDO SEED DE DATOS (ATLAS) ---");

            if (usuarioService.buscarPorEmail("admin@cinetrack.com").isEmpty()) {
                Usuario admin = usuarioService.registrar("admin@cinetrack.com", "admin123", "PREMIUM");
                admin.setRol("ADMIN");
                usuarioService.guardar(admin);

                Perfil perfil = perfilService.crearPerfil(admin, "Admin", "avatar1");
                log.info("✓ Usuario admin creado: {} con perfil '{}'", admin.getEmail(), perfil.getNombre());
            } else {
                log.info("✓ Usuario admin ya existe en BBDD.");
            }

            log.info("--- SEED FINALIZADO ---");
        };
    }
}
