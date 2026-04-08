package com.cinetrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Redirigir la raíz ("/") directamente al catálogo de películas
    @GetMapping("/")
    public String home() {
        return "redirect:/peliculas";
    }
}
