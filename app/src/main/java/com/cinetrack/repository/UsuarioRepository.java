package com.cinetrack.repository;

import com.cinetrack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
