package com.cinetrack.service;

import java.util.List;
import java.util.Optional;

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
        // Una sola query: SELECT * FROM peliculas WHERE genero_id IN (?,...)
        // Antes: N queries en bucle (una por genero) — ineficiente con catalogo grande
        return peliculaRepository.findByGeneroIdIn(generoIds);
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

    public long contar() {
        return peliculaRepository.count();
    }

    public List<Pelicula> obtenerRecientes(int limite) {
        return peliculaRepository.findTop5ByOrderByFechaCreacionDesc();
    }
}
