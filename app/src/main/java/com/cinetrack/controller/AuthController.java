package com.cinetrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinetrack.model.Genero;
import com.cinetrack.model.Perfil;
import com.cinetrack.model.Usuario;
import com.cinetrack.service.GeneroService;
import com.cinetrack.service.PerfilService;
import com.cinetrack.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;
    private final PerfilService perfilService;
    private final GeneroService generoService;

    @Autowired
    public AuthController(UsuarioService usuarioService, PerfilService perfilService, GeneroService generoService) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;
        this.generoService = generoService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Email o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado sesión correctamente");
        }
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registroForm() {
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registroProcesar(@RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String passwordConfirm,
                                   HttpSession session,
                                   RedirectAttributes redirect) {
        if (!password.equals(passwordConfirm)) {
            redirect.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/registro";
        }

        if (usuarioService.buscarPorEmail(email).isPresent()) {
            redirect.addFlashAttribute("error", "Ya existe una cuenta con ese email");
            return "redirect:/registro";
        }

        session.setAttribute("registroEmail", email);
        session.setAttribute("registroPassword", password);
        return "redirect:/registro/plan";
    }

    @GetMapping("/registro/plan")
    public String planForm(HttpSession session) {
        if (session.getAttribute("registroEmail") == null) {
            return "redirect:/registro";
        }
        return "auth/plan";
    }

    @PostMapping("/registro/plan")
    public String planProcesar(@RequestParam String plan,
                               HttpSession session,
                               RedirectAttributes redirect) {
        String email = (String) session.getAttribute("registroEmail");
        String password = (String) session.getAttribute("registroPassword");

        if (email == null || password == null) {
            return "redirect:/registro";
        }

        Usuario usuario = usuarioService.registrar(email, password, plan);

        // Limpiar datos temporales de sesión
        session.removeAttribute("registroEmail");
        session.removeAttribute("registroPassword");

        // Auto-login después de registrarse
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), null,
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return "redirect:/registro/perfil";
    }

    @GetMapping("/registro/perfil")
    public String crearPerfilForm(Model model) {
        List<Genero> generos = generoService.obtenerTodos();
        model.addAttribute("generos", generos);
        model.addAttribute("avatares", List.of("avatar1", "avatar2", "avatar3", "avatar4", "avatar5", "avatar6"));
        return "auth/crear-perfil";
    }

    @PostMapping("/registro/perfil")
    public String crearPerfilProcesar(@RequestParam String nombre,
                                      @RequestParam String avatar,
                                      @RequestParam(required = false) List<Integer> generoIds,
                                      HttpSession session) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        Perfil perfil = perfilService.crearPerfil(usuario, nombre, avatar);

        if (generoIds != null && !generoIds.isEmpty()) {
            List<Genero> generos = generoIds.stream()
                    .map(id -> generoService.obtenerPorId(id).orElse(null))
                    .filter(g -> g != null)
                    .toList();
            perfilService.guardarPreferenciasGenero(perfil, generos);
        }

        session.setAttribute("perfilActivo", perfil);
        return "redirect:/inicio";
    }
}
