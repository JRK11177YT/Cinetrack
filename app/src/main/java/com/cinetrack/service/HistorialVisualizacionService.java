package com.cinetrack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinetrack.model.HistorialVisualizacion;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.repository.HistorialVisualizacionRepository;

@Service
public class HistorialVisualizacionService {

    private final HistorialVisualizacionRepository historialRepository;

    @Autowired
    public HistorialVisualizacionService(HistorialVisualizacionRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public List<HistorialVisualizacion> obtenerSeguirViendo(Integer perfilId) {
        return historialRepository.findByPerfilIdAndCompletadaFalseOrderByFechaUltimaVisualizacionDesc(perfilId);
    }

    public Optional<HistorialVisualizacion> obtenerProgreso(Integer perfilId, Integer peliculaId) {
        return historialRepository.findByPerfilIdAndPeliculaId(perfilId, peliculaId);
    }

    public HistorialVisualizacion actualizarProgreso(Perfil perfil, Pelicula pelicula, int segundos) {
        HistorialVisualizacion historial = historialRepository
                .findByPerfilIdAndPeliculaId(perfil.getId(), pelicula.getId())
                .orElseGet(() -> {
                    HistorialVisualizacion nuevo = new HistorialVisualizacion();
                    nuevo.setPerfil(perfil);
                    nuevo.setPelicula(pelicula);
                    return nuevo;
                });

        historial.setProgresoSegundos(segundos);
        historial.setCompletada(segundos >= pelicula.getDuracion() * 60);
        historial.setFechaUltimaVisualizacion(java.time.LocalDateTime.now());
        return historialRepository.save(historial);
    }
}
