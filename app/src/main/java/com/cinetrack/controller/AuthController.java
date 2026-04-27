package com.cinetrack.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinetrack.model.Genero;
import com.cinetrack.model.Perfil;
import com.cinetrack.model.Usuario;
import com.cinetrack.service.GeneroService;
import com.cinetrack.service.PerfilService;
import com.cinetrack.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   RedirectAttributes redirect) {
        if (!password.equals(passwordConfirm)) {
            redirect.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/registro";
        }

        if (usuarioService.buscarPorEmail(email).isPresent()) {
            redirect.addFlashAttribute("error", "Ya existe una cuenta con ese email");
            return "redirect:/registro";
        }

        // BUG 5 FIX: registrar usuario inmediatamente con plan por defecto.
        // Nunca se almacena la contraseña en texto plano en la sesión HTTP.
        Usuario usuario = usuarioService.registrar(email, password, "BASICO");

        // Auto-login tras el registro para proteger el resto del flujo
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), null,
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);

        return "redirect:/registro/plan";
    }

    @GetMapping("/registro/plan")
    public String planForm() {
        // Si no está autenticado (acceso directo sin registro), redirigir
        String principal = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
        if (principal == null || "anonymousUser".equals(principal)) {
            return "redirect:/registro";
        }
        return "auth/plan";
    }

    @PostMapping("/registro/plan")
    public String planProcesar(@RequestParam String plan,
                               RedirectAttributes redirect) {
        // BUG 5 FIX: el usuario ya está autenticado; no se toca la sesión con credenciales
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElse(null);
        if (usuario == null) {
            return "redirect:/registro";
        }

        List<String> planesValidos = List.of("BASICO", "ESTANDAR", "PREMIUM");
        if (!planesValidos.contains(plan)) {
            redirect.addFlashAttribute("error", "Plan no válido");
            return "redirect:/registro/plan";
        }

        usuario.setPlan(plan);
        usuarioService.guardar(usuario);

        return "redirect:/registro/perfil";
    }

    @GetMapping("/registro/perfil")
    public String crearPerfilForm(Model model) {
        List<Genero> generos = generoService.obtenerTodos();
        model.addAttribute("generos", generos);
        return "auth/crear-perfil";
    }

    @PostMapping("/registro/perfil")
    public String crearPerfilProcesar(@RequestParam String nombre,
                                      @RequestParam(required = false) String avatar,
                                      @RequestParam(required = false) MultipartFile avatarFile,
                                      @RequestParam(required = false) List<Integer> generoIds,
                                      HttpSession session,
                                      RedirectAttributes redirect) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        // BUG 2 FIX: verificar límite de perfiles por plan antes de crear
        long perfilesActuales = perfilService.contarPerfilesPorUsuario(usuario.getId());
        if (perfilesActuales >= usuario.getMaxPerfiles()) {
            redirect.addFlashAttribute("error",
                    "Has alcanzado el límite de perfiles de tu plan (" + usuario.getMaxPerfiles() + ")");
            return "redirect:/perfiles";
        }

        String avatarUrl = (avatar != null && !avatar.isBlank()) ? avatar : "initial";
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String filename = guardarAvatar(avatarFile);
            if (filename != null) {
                avatarUrl = "custom:" + filename;
            }
        }

        Perfil perfil = perfilService.crearPerfil(usuario, nombre, avatarUrl);

        if (generoIds != null && !generoIds.isEmpty()) {
            List<Genero> generos = generoIds.stream()
                    .map(id -> generoService.obtenerPorId(id).orElse(null))
                    .filter(g -> g != null)
                    .toList();
            perfilService.guardarPreferenciasGenero(perfil, generos);
        }

        session.setAttribute("perfilActivoId", perfil.getId());
        return "redirect:/inicio";
    }

    private static final String UPLOAD_DIR = "uploads/avatars/";

    private String guardarAvatar(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return null;
        }
        if (file.getSize() > 2 * 1024 * 1024) {
            return null;
        }
        String ext = contentType.equals("image/png") ? ".png" : ".jpg";
        String filename = UUID.randomUUID() + ext;
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
        Files.createDirectories(uploadPath);
        file.transferTo(uploadPath.resolve(filename).toFile());
        return filename;
    }
}
