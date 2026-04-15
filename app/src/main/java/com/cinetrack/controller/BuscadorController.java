package com.cinetrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinetrack.model.Pelicula;
import com.cinetrack.service.PeliculaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/buscar")
public class BuscadorController {

    private final PeliculaService peliculaService;

    @Autowired
    public BuscadorController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public String buscar(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                         Model model, HttpSession session) {

        Integer perfilId = (Integer) session.getAttribute("perfilActivoId");
        if (perfilId == null) {
            return "redirect:/perfiles";
        }

        String query = q.trim();
        List<Pelicula> resultados = query.isEmpty()
                ? List.of()
                : peliculaService.buscarPorTitulo(query);

        model.addAttribute("query", query);
        model.addAttribute("resultados", resultados);
        model.addAttribute("totalResultados", resultados.size());
        return "buscar/index";
    }
}
