package com.cinetrack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinetrack.model.Genero;
import com.cinetrack.model.Perfil;
import com.cinetrack.model.PerfilGenero;
import com.cinetrack.model.Usuario;
import com.cinetrack.repository.PerfilGeneroRepository;
import com.cinetrack.repository.PerfilRepository;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final PerfilGeneroRepository perfilGeneroRepository;

    @Autowired
    public PerfilService(PerfilRepository perfilRepository, PerfilGeneroRepository perfilGeneroRepository) {
        this.perfilRepository = perfilRepository;
        this.perfilGeneroRepository = perfilGeneroRepository;
    }

    public List<Perfil> obtenerPerfilesPorUsuario(Integer usuarioId) {
        return perfilRepository.findByUsuarioIdAndActivoTrue(usuarioId);
    }

    public Optional<Perfil> obtenerPorId(Integer id) {
        return perfilRepository.findById(id);
    }

    public Perfil guardar(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    public Perfil crearPerfil(Usuario usuario, String nombre, String avatarUrl) {
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        perfil.setNombre(nombre);
        perfil.setAvatarUrl(avatarUrl);
        return perfilRepository.save(perfil);
    }

    public long contarPerfilesPorUsuario(Integer usuarioId) {
        return perfilRepository.countByUsuarioId(usuarioId);
    }

    @Transactional
    public void guardarPreferenciasGenero(Perfil perfil, List<Genero> generos) {
        perfilGeneroRepository.deleteByPerfilId(perfil.getId());
        for (Genero genero : generos) {
            PerfilGenero pg = new PerfilGenero();
            pg.setPerfil(perfil);
            pg.setGenero(genero);
            perfilGeneroRepository.save(pg);
        }
    }

    public List<PerfilGenero> obtenerPreferenciasGenero(Integer perfilId) {
        return perfilGeneroRepository.findByPerfilId(perfilId);
    }
}
