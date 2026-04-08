package com.cinetrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinetrack.model.Genero;
import com.cinetrack.service.GeneroService;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    public String listarGeneros(Model model) {
        List<Genero> listaGeneros = generoService.obtenerTodos();
        model.addAttribute("generos", listaGeneros);
        return "generos/index";
    }
}
