package com.cinetrack.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinetrack.model.MiLista;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.service.MiListaService;
import com.cinetrack.service.PeliculaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mi-lista")
public class MiListaController {

    private final MiListaService miListaService;
    private final PeliculaService peliculaService;

    @Autowired
    public MiListaController(MiListaService miListaService, PeliculaService peliculaService) {
        this.miListaService = miListaService;
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public String miLista(Model model, HttpSession session) {
        Perfil perfil = (Perfil) session.getAttribute("perfilActivo");
        List<MiLista> lista = miListaService.obtenerListaPorPerfil(perfil.getId());
        model.addAttribute("miLista", lista);
        model.addAttribute("perfil", perfil);
        return "mi-lista/index";
    }

    @PostMapping("/agregar/{peliculaId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> agregar(@PathVariable Integer peliculaId, HttpSession session) {
        Perfil perfil = (Perfil) session.getAttribute("perfilActivo");
        Pelicula pelicula = peliculaService.obtenerPorId(peliculaId).orElseThrow();
        miListaService.agregar(perfil, pelicula);
        return ResponseEntity.ok(Map.of("success", true, "message", "Añadido a Mi Lista"));
    }

    @DeleteMapping("/eliminar/{peliculaId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer peliculaId, HttpSession session) {
        Perfil perfil = (Perfil) session.getAttribute("perfilActivo");
        miListaService.eliminar(perfil.getId(), peliculaId);
        return ResponseEntity.ok(Map.of("success", true, "message", "Eliminado de Mi Lista"));
    }
}
