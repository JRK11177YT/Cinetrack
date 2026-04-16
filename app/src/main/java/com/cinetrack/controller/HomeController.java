package com.cinetrack.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cinetrack.model.Genero;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.service.GeneroService;
import com.cinetrack.service.PeliculaService;
import com.cinetrack.service.PerfilService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final PeliculaService peliculaService;
    private final GeneroService generoService;
    private final PerfilService perfilService;

    @Autowired
    public HomeController(PeliculaService peliculaService, GeneroService generoService,
                          PerfilService perfilService) {
        this.peliculaService = peliculaService;
        this.generoService = generoService;
        this.perfilService = perfilService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio(Model model, HttpSession session) {
        Integer perfilId = (Integer) session.getAttribute("perfilActivoId");
        if (perfilId == null) {
            return "redirect:/perfiles";
        }
        Perfil perfil = perfilService.obtenerPorId(perfilId).orElse(null);
        if (perfil == null) {
            session.removeAttribute("perfilActivoId");
            return "redirect:/perfiles";
        }

        // Película destacada para el Hero Banner
        List<Pelicula> destacadas = peliculaService.obtenerDestacadas();
        if (!destacadas.isEmpty()) {
            model.addAttribute("heroMovie", destacadas.get(0));
        }

        // Novedades carousel (películas marcadas como novedad)
        List<Pelicula> novedades = peliculaService.obtenerNovedades();
        model.addAttribute("novedades", novedades);

        // Películas por género (para los carruseles)
        List<Genero> generos = generoService.obtenerTodos();
        Map<Genero, List<Pelicula>> peliculasPorGenero = new LinkedHashMap<>();
        for (Genero genero : generos) {
            List<Pelicula> peliculas = peliculaService.obtenerPorGenero(genero.getId());
            if (!peliculas.isEmpty()) {
                peliculasPorGenero.put(genero, peliculas);
            }
        }
        model.addAttribute("peliculasPorGenero", peliculasPorGenero);

        // Destacadas (Top 10 style)
        model.addAttribute("destacadas", destacadas);
        model.addAttribute("perfil", perfil);

        return "home/index";
    }
}
