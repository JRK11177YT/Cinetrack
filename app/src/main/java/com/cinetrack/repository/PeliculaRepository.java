package com.cinetrack.repository;

import com.cinetrack.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    // SELECT * FROM peliculas WHERE destacada = true
    List<Pelicula> findByDestacadaTrue();

    // SELECT * FROM peliculas WHERE genero_id = ?
    List<Pelicula> findByGeneroId(Integer generoId);

    // SELECT * FROM peliculas WHERE titulo LIKE '%texto%'
    List<Pelicula> findByTituloContainingIgnoreCase(String texto);
}
