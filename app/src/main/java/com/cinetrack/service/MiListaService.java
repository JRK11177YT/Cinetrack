package com.cinetrack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinetrack.model.MiLista;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.repository.MiListaRepository;

@Service
public class MiListaService {

    private final MiListaRepository miListaRepository;

    @Autowired
    public MiListaService(MiListaRepository miListaRepository) {
        this.miListaRepository = miListaRepository;
    }

    public List<MiLista> obtenerListaPorPerfil(Integer perfilId) {
        return miListaRepository.findByPerfilIdOrderByFechaAgregadoDesc(perfilId);
    }

    public boolean estaEnMiLista(Integer perfilId, Integer peliculaId) {
        return miListaRepository.existsByPerfilIdAndPeliculaId(perfilId, peliculaId);
    }

    public MiLista agregar(Perfil perfil, Pelicula pelicula) {
        Optional<MiLista> existente = miListaRepository.findByPerfilIdAndPeliculaId(perfil.getId(), pelicula.getId());
        if (existente.isPresent()) {
            return existente.get();
        }
        MiLista nueva = new MiLista();
        nueva.setPerfil(perfil);
        nueva.setPelicula(pelicula);
        return miListaRepository.save(nueva);
    }

    public void eliminar(Integer perfilId, Integer peliculaId) {
        miListaRepository.findByPerfilIdAndPeliculaId(perfilId, peliculaId)
                .ifPresent(miListaRepository::delete);
    }
}
