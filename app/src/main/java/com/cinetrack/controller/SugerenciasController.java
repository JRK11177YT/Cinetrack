package com.cinetrack.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinetrack.model.Genero;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.model.PerfilGenero;
import com.cinetrack.repository.PerfilGeneroRepository;
import com.cinetrack.service.PeliculaService;
import com.cinetrack.service.PerfilService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sugerencias")
public class SugerenciasController {

    private final PeliculaService peliculaService;
    private final PerfilService perfilService;
    private final PerfilGeneroRepository perfilGeneroRepository;

    @Autowired
    public SugerenciasController(PeliculaService peliculaService,
                                  PerfilService perfilService,
                                  PerfilGeneroRepository perfilGeneroRepository) {
        this.peliculaService = peliculaService;
        this.perfilService = perfilService;
        this.perfilGeneroRepository = perfilGeneroRepository;
    }

    @GetMapping
    public String sugerencias(Model model, HttpSession session) {
        Integer perfilId = (Integer) session.getAttribute("perfilActivoId");
        if (perfilId == null) {
            return "redirect:/perfiles";
        }

        Perfil perfil = perfilService.obtenerPorId(perfilId).orElse(null);
        if (perfil == null) {
            session.removeAttribute("perfilActivoId");
            return "redirect:/perfiles";
        }

        // Géneros preferidos del perfil activo
        List<PerfilGenero> preferencias = perfilGeneroRepository.findByPerfilId(perfilId);

        if (preferencias.isEmpty()) {
            // Sin preferencias: mostrar todo el catálogo
            model.addAttribute("perfil", perfil);
            model.addAttribute("tienePreferencias", false);
            model.addAttribute("peliculasPorGenero", Map.of());
            model.addAttribute("totalPeliculas", 0);
            return "sugerencias/index";
        }

        // Agrupar películas por género preferido
        Map<Genero, List<Pelicula>> peliculasPorGenero = new LinkedHashMap<>();
        for (PerfilGenero pg : preferencias) {
            Genero genero = pg.getGenero();
            List<Pelicula> peliculas = peliculaService.obtenerPorGenero(genero.getId());
            if (!peliculas.isEmpty()) {
                peliculasPorGenero.put(genero, peliculas);
            }
        }

        int total = peliculasPorGenero.values().stream()
                .mapToInt(List::size)
                .sum();

        model.addAttribute("perfil", perfil);
        model.addAttribute("tienePreferencias", true);
        model.addAttribute("generosPreferidos", preferencias.stream()
                .map(PerfilGenero::getGenero)
                .collect(Collectors.toList()));
        model.addAttribute("peliculasPorGenero", peliculasPorGenero);
        model.addAttribute("totalPeliculas", total);
        return "sugerencias/index";
    }
}
