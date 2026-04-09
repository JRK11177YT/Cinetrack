package com.cinetrack.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("emailCuenta", email);
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

        session.setAttribute("perfilActivoId", perfil.getId());
        return "redirect:/inicio";
    }

    // ==========================================
    // GESTIONAR PERFILES
    // ==========================================

    @GetMapping("/gestionar")
    public String gestionarPerfiles(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        List<Perfil> perfiles = perfilService.obtenerPerfilesPorUsuario(usuario.getId());

        model.addAttribute("perfiles", perfiles);
        model.addAttribute("emailCuenta", email);
        return "perfiles/gestionar";
    }

    @GetMapping("/editar/{id}")
    public String editarPerfilForm(@PathVariable Integer id, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        Perfil perfil = perfilService.obtenerPorId(id).orElseThrow();

        if (!perfil.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/perfiles/gestionar";
        }

        model.addAttribute("perfil", perfil);
        return "perfiles/editar";
    }

    @PostMapping("/editar/{id}")
    public String editarPerfil(@PathVariable Integer id,
                               @RequestParam String nombre,
                               @RequestParam(required = false) String avatarPreset,
                               @RequestParam(required = false) MultipartFile avatarFile,
                               RedirectAttributes redirect,
                               HttpSession session) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        Perfil perfil = perfilService.obtenerPorId(id).orElseThrow();

        if (!perfil.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/perfiles/gestionar";
        }

        perfil.setNombre(nombre.trim());

        // Avatar personalizado subido
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String filename = guardarAvatar(avatarFile);
            if (filename != null) {
                perfil.setAvatarUrl("custom:" + filename);
            }
        } else if (avatarPreset != null && !avatarPreset.isBlank()) {
            perfil.setAvatarUrl(avatarPreset);
        }

        perfilService.guardar(perfil);
        redirect.addFlashAttribute("mensaje", "Perfil actualizado correctamente");
        return "redirect:/perfiles/gestionar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarPerfil(@PathVariable Integer id, RedirectAttributes redirect, HttpSession session) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        Perfil perfil = perfilService.obtenerPorId(id).orElseThrow();

        if (!perfil.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/perfiles/gestionar";
        }

        // No permitir borrar el último perfil
        long count = perfilService.contarPerfilesPorUsuario(usuario.getId());
        if (count <= 1) {
            redirect.addFlashAttribute("error", "Debes tener al menos un perfil");
            return "redirect:/perfiles/gestionar";
        }

        // Si estamos borrando el perfil activo, limpiar sesión
        Integer perfilActivoId = (Integer) session.getAttribute("perfilActivoId");
        if (perfilActivoId != null && perfilActivoId.equals(id)) {
            session.removeAttribute("perfilActivoId");
        }

        perfilService.eliminar(id);
        redirect.addFlashAttribute("mensaje", "Perfil eliminado");
        return "redirect:/perfiles/gestionar";
    }

    // ==========================================
    // UTILIDADES
    // ==========================================

    private static final String UPLOAD_DIR = "uploads/avatars/";

    private String guardarAvatar(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return null;
        }
        if (file.getSize() > 2 * 1024 * 1024) { // Max 2MB
            return null;
        }

        String ext = contentType.equals("image/png") ? ".png" : ".jpg";
        String filename = UUID.randomUUID() + ext;

        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(filename);
        file.transferTo(filePath.toFile());

        return filename;
    }
}
