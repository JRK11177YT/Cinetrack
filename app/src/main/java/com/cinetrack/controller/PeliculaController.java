package com.cinetrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.service.MiListaService;
import com.cinetrack.service.PeliculaService;
import com.cinetrack.service.PerfilService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PeliculaController {

    private final PeliculaService peliculaService;
    private final MiListaService miListaService;
    private final PerfilService perfilService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService, MiListaService miListaService,
                              PerfilService perfilService) {
        this.peliculaService = peliculaService;
        this.miListaService = miListaService;
        this.perfilService = perfilService;
    }

    @GetMapping("/pelicula/{id}")
    public String detalle(@PathVariable Integer id, Model model, HttpSession session) {
        Integer perfilId = (Integer) session.getAttribute("perfilActivoId");
        Perfil perfil = perfilService.obtenerPorId(perfilId).orElseThrow();
        Pelicula pelicula = peliculaService.obtenerPorId(id).orElseThrow();

        boolean enMiLista = miListaService.estaEnMiLista(perfil.getId(), id);

        List<Pelicula> relacionadas = peliculaService.obtenerPorGenero(pelicula.getGenero().getId())
                .stream()
                .filter(p -> !p.getId().equals(id))
                .limit(6)
                .collect(Collectors.toList());

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("enMiLista", enMiLista);
        model.addAttribute("perfil", perfil);
        model.addAttribute("relacionadas", relacionadas);

        return "peliculas/detalle";
    }
}
