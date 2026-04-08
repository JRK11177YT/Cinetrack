package com.cinetrack.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cinetrack.model.Genero;
import com.cinetrack.model.Pelicula;
import com.cinetrack.service.GeneroService;
import com.cinetrack.service.PeliculaService;

@Configuration
public class DatabaseSeederConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeederConfig.class);

    @Bean
    CommandLineRunner initDatabase(GeneroService generoService, PeliculaService peliculaService) {
        return args -> {
            log.info("--- INICIANDO PRUEBA GESTION DE DATOS (ATLAS) ---");

            // 1. Crear y guardar un género de prueba (si no existe ya)
            Optional<Genero> generoExistente = generoService.buscarPorNombre("Accion");
            Genero generoAccion;
            
            if (generoExistente.isEmpty()) {
                Genero nuevoGenero = new Genero();
                nuevoGenero.setNombre("Accion");
                nuevoGenero.setDescripcion("Peliculas con explosiones y disparos");
                generoAccion = generoService.guardar(nuevoGenero);
                log.info("✓ Genero Guardado: " + generoAccion.getNombre() + " (ID: " + generoAccion.getId() + ")");
            } else {
                generoAccion = generoExistente.get();
                log.info("✓ Genero Encontrado: " + generoAccion.getNombre() + " (ID: " + generoAccion.getId() + ")");
            }

            // 2. Crear y guardar una película asociada a ese género (si no existe)
            if (peliculaService.buscarPorTitulo("Die Hard").isEmpty()) {
                Pelicula peli = new Pelicula();
                peli.setTitulo("Die Hard");
                peli.setDescripcion("Un policia de NY atrapado en un rascacielos con terroristas.");
                peli.setDuracion(132);
                peli.setAnio(1988);
                peli.setDestacada(true);
                peli.setGenero(generoAccion); // RELACIÓN JPA

                Pelicula guardada = peliculaService.guardar(peli);
                log.info("✓ Pelicula Guardada: " + guardada.getTitulo() + " (ID: " + guardada.getId() + ")");
            } else {
                log.info("✓ La pelicula Die Hard ya existe en BBDD.");
            }

            log.info("--- PRUEBA FINALIZADA CON ÉXITO ---");
        };
    }
}
