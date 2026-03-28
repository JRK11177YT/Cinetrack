package com.cinetrack.repository;

import com.cinetrack.model.HistorialVisualizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistorialVisualizacionRepository extends JpaRepository<HistorialVisualizacion, Integer> {
    
    // Para la sección "Continuar Viendo" principal
    List<HistorialVisualizacion> findByUsuarioIdAndCompletadaFalse(Integer usuarioId);
    
    // Para saber por dónde va un usuario en una peli exacta
    Optional<HistorialVisualizacion> findByUsuarioIdAndPeliculaId(Integer usuarioId, Integer peliculaId);
}
