package com.cinetrack.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinetrack.model.Pelicula;
import com.cinetrack.repository.PeliculaRepository;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public List<Pelicula> obtenerTodas() {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> obtenerPorId(Integer id) {
        return peliculaRepository.findById(id);
    }

    public List<Pelicula> obtenerDestacadas() {
        return peliculaRepository.findByDestacadaTrue();
    }

    public List<Pelicula> obtenerNovedades() {
        return peliculaRepository.findByNovedadTrue();
    }

    public List<Pelicula> buscarPorTitulo(String texto) {
        return peliculaRepository.findByTituloContainingIgnoreCase(texto);
    }

    public List<Pelicula> obtenerPorGenero(Integer generoId) {
        return peliculaRepository.findByGeneroId(generoId);
    }

    public List<Pelicula> obtenerPorGeneros(List<Integer> generoIds) {
        if (generoIds == null || generoIds.isEmpty()) {
            return peliculaRepository.findAll();
        }
        return generoIds.stream()
                .flatMap(gid -> peliculaRepository.findByGeneroId(gid).stream())
                .collect(Collectors.toList());
    }

    public Pelicula guardar(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public void eliminar(Integer id) {
        peliculaRepository.deleteById(id);
    }

    public void eliminarTodas() {
        peliculaRepository.deleteAll();
    }
}
