package com.cinetrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.MiLista;

@Repository
public interface MiListaRepository extends JpaRepository<MiLista, Integer> {
    
    List<MiLista> findByPerfilIdOrderByFechaAgregadoDesc(Integer perfilId);

    Optional<MiLista> findByPerfilIdAndPeliculaId(Integer perfilId, Integer peliculaId);

    boolean existsByPerfilIdAndPeliculaId(Integer perfilId, Integer peliculaId);

    void deleteByPerfilId(Integer perfilId);
}
