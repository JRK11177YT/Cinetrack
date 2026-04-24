package com.cinetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    
    // SELECT * FROM generos WHERE nombre = ?
    Optional<Genero> findByNombreIgnoreCase(String nombre);

    // SELECT COUNT(*) FROM generos
    long count();
}
