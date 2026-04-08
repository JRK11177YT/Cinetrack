package com.cinetrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.MiLista;

@Repository
public interface MiListaRepository extends JpaRepository<MiLista, Integer> {
    
    // Obtiene toda la lista de películas guardadas de un usuario en concreto
    // SELECT * FROM mi_lista WHERE usuario_id = ?
    List<MiLista> findByUsuarioId(Integer usuarioId);
}
