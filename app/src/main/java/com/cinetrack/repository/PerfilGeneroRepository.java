package com.cinetrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinetrack.model.PerfilGenero;

@Repository
public interface PerfilGeneroRepository extends JpaRepository<PerfilGenero, Integer> {

    List<PerfilGenero> findByPerfilId(Integer perfilId);

    void deleteByPerfilId(Integer perfilId);
}
