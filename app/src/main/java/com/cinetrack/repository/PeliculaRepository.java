package com.cinetrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    // SELECT * FROM peliculas WHERE destacada = true
    List<Pelicula> findByDestacadaTrue();

    // SELECT * FROM peliculas WHERE genero_id = ?
    List<Pelicula> findByGeneroId(Integer generoId);

    // SELECT * FROM peliculas WHERE titulo LIKE '%texto%'
    List<Pelicula> findByTituloContainingIgnoreCase(String texto);

    // SELECT * FROM peliculas WHERE novedad = true
    List<Pelicula> findByNovedadTrue();

    // SELECT * FROM peliculas WHERE genero_id IN (...) — una sola query para multiples generos
    List<Pelicula> findByGeneroIdIn(List<Integer> generoIds);

    // SELECT COUNT(*) FROM peliculas
    long count();

    // SELECT * FROM peliculas ORDER BY fecha_creacion DESC LIMIT 5
    List<Pelicula> findTop5ByOrderByFechaCreacionDesc();
}
