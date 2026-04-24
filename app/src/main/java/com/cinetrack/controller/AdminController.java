package com.cinetrack.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinetrack.model.Genero;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Usuario;
import com.cinetrack.service.GeneroService;
import com.cinetrack.service.PeliculaService;
import com.cinetrack.service.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final PeliculaService peliculaService;
    private final GeneroService generoService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(PeliculaService peliculaService, GeneroService generoService,
                           UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.peliculaService = peliculaService;
        this.generoService = generoService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================================
    // DASHBOARD
    // ==========================================

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalPeliculas", peliculaService.contar());
        model.addAttribute("totalUsuarios", usuarioService.contar());
        model.addAttribute("totalGeneros", generoService.contar());
        model.addAttribute("peliculasRecientes", peliculaService.obtenerRecientes(5));
        return "admin/dashboard";
    }

    // ==========================================
    // PELÍCULAS
    // ==========================================

    @GetMapping("/peliculas")
    public String listaPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaService.obtenerTodas());
        return "admin/peliculas/index";
    }

    @GetMapping("/peliculas/nueva")
    public String nuevaPeliculaForm(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("generos", generoService.obtenerTodos());
        model.addAttribute("accion", "nueva");
        return "admin/peliculas/form";
    }

    @PostMapping("/peliculas/nueva")
    public String crearPelicula(@RequestParam String titulo,
                                @RequestParam String descripcion,
                                @RequestParam Integer duracion,
                                @RequestParam Integer anio,
                                @RequestParam(required = false) String director,
                                @RequestParam(required = false) Double valoracionImdb,
                                @RequestParam(required = false) MultipartFile imagenFile,
                                @RequestParam(required = false) MultipartFile videoFile,
                                @RequestParam(required = false) MultipartFile heroFile,
                                @RequestParam Integer generoId,
                                @RequestParam(defaultValue = "false") boolean destacada,
                                @RequestParam(defaultValue = "false") boolean novedad,
                                RedirectAttributes redirect) {
        try {
            Pelicula p = new Pelicula();
            p.setTitulo(titulo.trim());
            p.setDescripcion(descripcion.trim());
            p.setDuracion(duracion);
            p.setAnio(anio);
            p.setDirector(director != null && !director.isBlank() ? director.trim() : null);
            p.setValoracionImdb(valoracionImdb);
            p.setDestacada(destacada);
            p.setNovedad(novedad);

            if (imagenFile != null && !imagenFile.isEmpty()) {
                String urlImagen = guardarArchivo(imagenFile, "imagenes", new String[]{"image/"}, 10L * 1024 * 1024);
                if (urlImagen != null) p.setUrlImagen(urlImagen);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String urlVideo = guardarArchivo(videoFile, "videos", new String[]{"video/"}, 500L * 1024 * 1024);
                if (urlVideo != null) p.setUrlVideo(urlVideo);
            }
            if (heroFile != null && !heroFile.isEmpty()) {
                String urlHero = guardarArchivo(heroFile, "heroes", new String[]{"image/"}, 10L * 1024 * 1024);
                if (urlHero != null) p.setUrlHero(urlHero);
            }

            Genero genero = generoService.obtenerPorId(generoId).orElseThrow();
            p.setGenero(genero);

            peliculaService.guardar(p);
            redirect.addFlashAttribute("mensaje", "Película \"" + p.getTitulo() + "\" creada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al crear la película: " + e.getMessage());
        }
        return "redirect:/admin/peliculas";
    }

    @GetMapping("/peliculas/editar/{id}")
    public String editarPeliculaForm(@PathVariable Integer id, Model model) {
        Pelicula p = peliculaService.obtenerPorId(id).orElseThrow();
        model.addAttribute("pelicula", p);
        model.addAttribute("generos", generoService.obtenerTodos());
        model.addAttribute("accion", "editar");
        return "admin/peliculas/form";
    }

    @PostMapping("/peliculas/editar/{id}")
    public String actualizarPelicula(@PathVariable Integer id,
                                     @RequestParam String titulo,
                                     @RequestParam String descripcion,
                                     @RequestParam Integer duracion,
                                     @RequestParam Integer anio,
                                     @RequestParam(required = false) String director,
                                     @RequestParam(required = false) Double valoracionImdb,
                                     @RequestParam(required = false) MultipartFile imagenFile,
                                     @RequestParam(required = false) MultipartFile videoFile,
                                     @RequestParam(required = false) MultipartFile heroFile,
                                     @RequestParam Integer generoId,
                                     @RequestParam(defaultValue = "false") boolean destacada,
                                     @RequestParam(defaultValue = "false") boolean novedad,
                                     RedirectAttributes redirect) {
        try {
            Pelicula p = peliculaService.obtenerPorId(id).orElseThrow();
            p.setTitulo(titulo.trim());
            p.setDescripcion(descripcion.trim());
            p.setDuracion(duracion);
            p.setAnio(anio);
            p.setDirector(director != null && !director.isBlank() ? director.trim() : null);
            p.setValoracionImdb(valoracionImdb);
            p.setDestacada(destacada);
            p.setNovedad(novedad);

            if (imagenFile != null && !imagenFile.isEmpty()) {
                borrarArchivoUrl(p.getUrlImagen());
                String urlImagen = guardarArchivo(imagenFile, "imagenes", new String[]{"image/"}, 10L * 1024 * 1024);
                if (urlImagen != null) p.setUrlImagen(urlImagen);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                borrarArchivoUrl(p.getUrlVideo());
                String urlVideo = guardarArchivo(videoFile, "videos", new String[]{"video/"}, 500L * 1024 * 1024);
                if (urlVideo != null) p.setUrlVideo(urlVideo);
            }
            if (heroFile != null && !heroFile.isEmpty()) {
                borrarArchivoUrl(p.getUrlHero());
                String urlHero = guardarArchivo(heroFile, "heroes", new String[]{"image/"}, 10L * 1024 * 1024);
                if (urlHero != null) p.setUrlHero(urlHero);
            }

            Genero genero = generoService.obtenerPorId(generoId).orElseThrow();
            p.setGenero(genero);

            peliculaService.guardar(p);
            redirect.addFlashAttribute("mensaje", "Película actualizada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al actualizar la película: " + e.getMessage());
        }
        return "redirect:/admin/peliculas";
    }

    @PostMapping("/peliculas/eliminar/{id}")
    public String eliminarPelicula(@PathVariable Integer id, RedirectAttributes redirect) {
        Pelicula p = peliculaService.obtenerPorId(id).orElseThrow();
        String titulo = p.getTitulo();
        borrarArchivoUrl(p.getUrlImagen());
        borrarArchivoUrl(p.getUrlVideo());
        borrarArchivoUrl(p.getUrlHero());
        peliculaService.eliminar(id);
        redirect.addFlashAttribute("mensaje", "Película \"" + titulo + "\" eliminada");
        return "redirect:/admin/peliculas";
    }

    @PostMapping("/peliculas/eliminar-todas")
    public String eliminarTodasPeliculas(RedirectAttributes redirect) {
        List<Pelicula> peliculas = peliculaService.obtenerTodas();
        for (Pelicula p : peliculas) {
            borrarArchivoUrl(p.getUrlImagen());
            borrarArchivoUrl(p.getUrlVideo());
            borrarArchivoUrl(p.getUrlHero());
        }
        peliculaService.eliminarTodas();
        redirect.addFlashAttribute("mensaje", "Se han eliminado " + peliculas.size() + " películas del catálogo");
        return "redirect:/admin/peliculas";
    }

    // ==========================================
    // USUARIOS
    // ==========================================

    @GetMapping("/usuarios")
    public String listaUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "admin/usuarios/index";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuarioForm(@PathVariable Integer id, Model model) {
        Usuario u = usuarioService.obtenerPorId(id).orElseThrow();
        model.addAttribute("usuario", u);
        return "admin/usuarios/form";
    }

    @PostMapping("/usuarios/editar/{id}")
    public String actualizarUsuario(@PathVariable Integer id,
                                    @RequestParam String plan,
                                    @RequestParam String rol,
                                    @RequestParam(defaultValue = "false") boolean activo,
                                    @RequestParam(required = false) String nuevaPassword,
                                    RedirectAttributes redirect) {
        Usuario u = usuarioService.obtenerPorId(id).orElseThrow();

        List<String> planesValidos = List.of("BASICO", "ESTANDAR", "PREMIUM");
        List<String> rolesValidos = List.of("USER", "ADMIN");
        if (!planesValidos.contains(plan) || !rolesValidos.contains(rol)) {
            redirect.addFlashAttribute("error", "Valores no válidos");
            return "redirect:/admin/usuarios";
        }

        u.setPlan(plan);
        u.setRol(rol);
        u.setActivo(activo);

        if (nuevaPassword != null && !nuevaPassword.isBlank()) {
            if (nuevaPassword.length() >= 6) {
                u.setPassword(passwordEncoder.encode(nuevaPassword));
            } else {
                redirect.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                return "redirect:/admin/usuarios/editar/" + id;
            }
        }

        usuarioService.guardar(u);
        redirect.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirect) {
        Usuario u = usuarioService.obtenerPorId(id).orElseThrow();
        if ("ADMIN".equals(u.getRol())) {
            redirect.addFlashAttribute("error", "No se puede eliminar un usuario administrador");
            return "redirect:/admin/usuarios";
        }
        String email = u.getEmail();
        usuarioService.eliminar(id);
        redirect.addFlashAttribute("mensaje", "Usuario \"" + email + "\" eliminado correctamente");
        return "redirect:/admin/usuarios";
    }

    // ==========================================
    // GÉNEROS
    // ==========================================

    @GetMapping("/generos")
    public String listaGeneros(Model model) {
        model.addAttribute("generos", generoService.obtenerTodos());
        return "admin/generos/index";
    }

    @GetMapping("/generos/nuevo")
    public String nuevoGeneroForm(Model model) {
        model.addAttribute("genero", new Genero());
        model.addAttribute("accion", "nuevo");
        return "admin/generos/form";
    }

    @PostMapping("/generos/nuevo")
    public String crearGenero(@RequestParam String nombre,
                              @RequestParam(required = false) String descripcion,
                              RedirectAttributes redirect) {
        if (generoService.buscarPorNombre(nombre.trim()).isPresent()) {
            redirect.addFlashAttribute("error", "Ya existe un género con ese nombre");
            return "redirect:/admin/generos/nuevo";
        }
        Genero g = new Genero();
        g.setNombre(nombre.trim());
        g.setDescripcion(descripcion != null && !descripcion.isBlank() ? descripcion.trim() : null);
        generoService.guardar(g);
        redirect.addFlashAttribute("mensaje", "Género \"" + g.getNombre() + "\" creado correctamente");
        return "redirect:/admin/generos";
    }

    @GetMapping("/generos/editar/{id}")
    public String editarGeneroForm(@PathVariable Integer id, Model model) {
        Genero g = generoService.obtenerPorId(id).orElseThrow();
        model.addAttribute("genero", g);
        model.addAttribute("accion", "editar");
        return "admin/generos/form";
    }

    @PostMapping("/generos/editar/{id}")
    public String actualizarGenero(@PathVariable Integer id,
                                   @RequestParam String nombre,
                                   @RequestParam(required = false) String descripcion,
                                   RedirectAttributes redirect) {
        Genero g = generoService.obtenerPorId(id).orElseThrow();
        g.setNombre(nombre.trim());
        g.setDescripcion(descripcion != null && !descripcion.isBlank() ? descripcion.trim() : null);
        generoService.guardar(g);
        redirect.addFlashAttribute("mensaje", "Género actualizado correctamente");
        return "redirect:/admin/generos";
    }

    @PostMapping("/generos/eliminar/{id}")
    public String eliminarGenero(@PathVariable Integer id, RedirectAttributes redirect) {
        Genero g = generoService.obtenerPorId(id).orElseThrow();
        String nombre = g.getNombre();
        generoService.eliminar(id);
        redirect.addFlashAttribute("mensaje", "Género \"" + nombre + "\" eliminado");
        return "redirect:/admin/generos";
    }

    // ==========================================
    // UTILIDADES
    // ==========================================

    private static final String UPLOAD_BASE = "uploads/peliculas/";

    private void borrarArchivoUrl(String url) {
        if (url == null || url.isBlank()) return;
        String relPath = url.startsWith("/") ? url.substring(1) : url;
        try {
            Files.deleteIfExists(Paths.get(relPath).toAbsolutePath());
        } catch (IOException e) {
            log.warn("No se pudo eliminar archivo: {}", url);
        }
    }

    private String guardarArchivo(MultipartFile file, String subfolder,
                                  String[] allowedPrefixes, long maxBytes) throws IOException {
        log.info("guardarArchivo: name={}, contentType={}, size={}, subfolder={}",
                file.getOriginalFilename(), file.getContentType(), file.getSize(), subfolder);

        if (file.getSize() > maxBytes) {
            log.warn("Archivo demasiado grande: {} > {} bytes", file.getSize(), maxBytes);
            return null;
        }

        String originalName = file.getOriginalFilename();
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf('.')).toLowerCase();
        }
        String filename = UUID.randomUUID() + ext;

        Path dir = Paths.get(UPLOAD_BASE + subfolder).toAbsolutePath();
        Files.createDirectories(dir);
        Path destino = dir.resolve(filename);
        file.transferTo(destino.toFile());

        log.info("Archivo guardado en: {}", destino);
        return "/uploads/peliculas/" + subfolder + "/" + filename;
    }
}
