package com.cinetrack.controller;

import com.cinetrack.model.Pelicula;
import com.cinetrack.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    // Inyectamos el servicio, NUNCA el repositorio directamente
    @Autowired
    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public String listarPeliculas(Model model) {
        // 1. Pedimos todas las películas a la base de datos
        List<Pelicula> listaPeliculas = peliculaService.obtenerTodas();
        
        // 2. Metemos las películas en la "mochila" (Model) para llevárnoslas al HTML
        model.addAttribute("peliculas", listaPeliculas);
        
        // 3. Devolvemos el nombre del archivo HTML (sin el .html) que Spring debe renderizar
        return "peliculas/index";
    }
}
