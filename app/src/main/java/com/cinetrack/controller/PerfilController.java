package com.cinetrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinetrack.model.Perfil;
import com.cinetrack.model.Usuario;
import com.cinetrack.service.PerfilService;
import com.cinetrack.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfiles")
public class PerfilController {

    private final PerfilService perfilService;
    private final UsuarioService usuarioService;

    @Autowired
    public PerfilController(PerfilService perfilService, UsuarioService usuarioService) {
        this.perfilService = perfilService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String seleccionarPerfil(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        List<Perfil> perfiles = perfilService.obtenerPerfilesPorUsuario(usuario.getId());

        if (perfiles.isEmpty()) {
            return "redirect:/registro/perfil";
        }

        model.addAttribute("perfiles", perfiles);
        model.addAttribute("maxPerfiles", usuario.getMaxPerfiles());
        model.addAttribute("puedeCrearMas", perfiles.size() < usuario.getMaxPerfiles());
        return "perfiles/seleccionar";
    }

    @PostMapping("/seleccionar/{id}")
    public String activarPerfil(@PathVariable Integer id, HttpSession session) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        Perfil perfil = perfilService.obtenerPorId(id).orElseThrow();

        // Verificar que el perfil pertenece al usuario autenticado
        if (!perfil.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/perfiles";
        }

        session.setAttribute("perfilActivo", perfil);
        return "redirect:/inicio";
    }
}
