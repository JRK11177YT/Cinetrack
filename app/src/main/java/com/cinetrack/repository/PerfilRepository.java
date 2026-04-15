package com.cinetrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    List<Perfil> findByUsuarioIdAndActivoTrue(Integer usuarioId);

    long countByUsuarioId(Integer usuarioId);
}
