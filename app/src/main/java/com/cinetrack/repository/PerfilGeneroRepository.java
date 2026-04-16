package com.cinetrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cinetrack.model.PerfilGenero;

@Repository
public interface PerfilGeneroRepository extends JpaRepository<PerfilGenero, Integer> {

    List<PerfilGenero> findByPerfilId(Integer perfilId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PerfilGenero pg WHERE pg.perfil.id = :perfilId")
    void deleteByPerfilId(@Param("perfilId") Integer perfilId);
}
