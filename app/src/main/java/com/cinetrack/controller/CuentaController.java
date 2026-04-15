package com.cinetrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinetrack.model.Usuario;
import com.cinetrack.service.UsuarioService;

@Controller
@RequestMapping("/cuenta")
public class CuentaController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CuentaController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String miCuenta(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        model.addAttribute("usuario", usuario);
        return "cuenta/index";
    }

    @PostMapping("/password")
    public String cambiarPassword(@RequestParam String passwordActual,
                                  @RequestParam String passwordNueva,
                                  @RequestParam String passwordConfirmar,
                                  RedirectAttributes redirect) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            redirect.addFlashAttribute("error", "La contraseña actual no es correcta");
            return "redirect:/cuenta";
        }

        if (passwordNueva.length() < 6) {
            redirect.addFlashAttribute("error", "La nueva contraseña debe tener al menos 6 caracteres");
            return "redirect:/cuenta";
        }

        if (!passwordNueva.equals(passwordConfirmar)) {
            redirect.addFlashAttribute("error", "Las contraseñas nuevas no coinciden");
            return "redirect:/cuenta";
        }

        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuarioService.guardar(usuario);

        redirect.addFlashAttribute("mensaje", "Contraseña actualizada correctamente");
        return "redirect:/cuenta";
    }

    @PostMapping("/plan")
    public String cambiarPlan(@RequestParam String plan, RedirectAttributes redirect) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        if (!plan.equals("BASICO") && !plan.equals("ESTANDAR") && !plan.equals("PREMIUM")) {
            redirect.addFlashAttribute("error", "Plan no válido");
            return "redirect:/cuenta";
        }

        usuario.setPlan(plan);
        usuarioService.guardar(usuario);

        redirect.addFlashAttribute("mensaje", "Plan actualizado a " + plan);
        return "redirect:/cuenta";
    }
}
