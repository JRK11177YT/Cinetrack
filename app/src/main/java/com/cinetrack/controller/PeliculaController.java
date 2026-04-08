package com.cinetrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cinetrack.model.HistorialVisualizacion;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.service.HistorialVisualizacionService;
import com.cinetrack.service.MiListaService;
import com.cinetrack.service.PeliculaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PeliculaController {

    private final PeliculaService peliculaService;
    private final MiListaService miListaService;
    private final HistorialVisualizacionService historialService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService, MiListaService miListaService,
                              HistorialVisualizacionService historialService) {
        this.peliculaService = peliculaService;
        this.miListaService = miListaService;
        this.historialService = historialService;
    }

    @GetMapping("/pelicula/{id}")
    public String detalle(@PathVariable Integer id, Model model, HttpSession session) {
        Perfil perfil = (Perfil) session.getAttribute("perfilActivo");
        Pelicula pelicula = peliculaService.obtenerPorId(id).orElseThrow();

        boolean enMiLista = miListaService.estaEnMiLista(perfil.getId(), id);
        HistorialVisualizacion progreso = historialService.obtenerProgreso(perfil.getId(), id).orElse(null);

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("enMiLista", enMiLista);
        model.addAttribute("progreso", progreso);
        model.addAttribute("perfil", perfil);

        return "peliculas/detalle";
    }
}
