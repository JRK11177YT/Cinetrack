package com.cinetrack.service;

import com.cinetrack.model.Genero;
import com.cinetrack.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public List<Genero> obtenerTodos() {
        return generoRepository.findAll();
    }

    public Optional<Genero> obtenerPorId(Integer id) {
        return generoRepository.findById(id);
    }

    public Optional<Genero> buscarPorNombre(String nombre) {
        return generoRepository.findByNombreIgnoreCase(nombre);
    }

    public Genero guardar(Genero genero) {
        return generoRepository.save(genero);
    }
}
