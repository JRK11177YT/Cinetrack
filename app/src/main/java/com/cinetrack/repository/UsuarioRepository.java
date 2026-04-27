package com.cinetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // ========================================================
    // QUERYS AUTOMÁTICAS (Magia de Spring Data JPA)
    // Spring leerá el nombre de los métodos y creará el SQL solo.
    // ========================================================

    // Esto automáticamente ejecuta: SELECT * FROM usuarios WHERE email = ?
    Optional<Usuario> findByEmail(String email);

    // Esto automáticamente ejecuta: SELECT * FROM usuarios WHERE activo = true
    Iterable<Usuario> findByActivoTrue();

    // SELECT COUNT(*) FROM usuarios
    long count();
}
