package com.cinetrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.HistorialVisualizacion;

@Repository
public interface HistorialVisualizacionRepository extends JpaRepository<HistorialVisualizacion, Integer> {
    
    List<HistorialVisualizacion> findByPerfilIdAndCompletadaFalseOrderByFechaUltimaVisualizacionDesc(Integer perfilId);
    
    Optional<HistorialVisualizacion> findByPerfilIdAndPeliculaId(Integer perfilId, Integer peliculaId);
}
