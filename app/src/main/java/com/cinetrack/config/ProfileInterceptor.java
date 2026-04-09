package com.cinetrack.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cinetrack.model.Perfil;
import com.cinetrack.model.Usuario;
import com.cinetrack.service.PerfilService;
import com.cinetrack.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ProfileInterceptor implements HandlerInterceptor {

    private final PerfilService perfilService;
    private final UsuarioService usuarioService;

    @Autowired
    public ProfileInterceptor(PerfilService perfilService, UsuarioService usuarioService) {
        this.perfilService = perfilService;
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // Si ya tiene perfil activo, dejar pasar
        if (session != null && session.getAttribute("perfilActivoId") != null) {
            return true;
        }

        // Obtener usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return true; // Spring Security se encarga de la autenticación
        }

        String email = auth.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElse(null);
        if (usuario == null) {
            return true;
        }

        // Comprobar si tiene perfiles
        List<Perfil> perfiles = perfilService.obtenerPerfilesPorUsuario(usuario.getId());
        if (perfiles.isEmpty()) {
            response.sendRedirect("/registro/perfil");
            return false;
        }

        // Tiene perfiles pero no ha seleccionado uno → ir a selección
        response.sendRedirect("/perfiles");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("currentUri", request.getRequestURI());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                boolean esAdmin = auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                modelAndView.addObject("esAdmin", esAdmin);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                Integer perfilId = (Integer) session.getAttribute("perfilActivoId");
                if (perfilId != null) {
                    perfilService.obtenerPorId(perfilId).ifPresent(perfil -> {
                        modelAndView.addObject("perfilActivo", perfil);
                    });
                }
            }
        }
    }
}
