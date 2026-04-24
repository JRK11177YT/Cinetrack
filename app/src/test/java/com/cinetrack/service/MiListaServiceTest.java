package com.cinetrack.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cinetrack.model.MiLista;
import com.cinetrack.model.Pelicula;
import com.cinetrack.model.Perfil;
import com.cinetrack.repository.MiListaRepository;

@ExtendWith(MockitoExtension.class)
class MiListaServiceTest {

    @Mock
    private MiListaRepository miListaRepository;

    @InjectMocks
    private MiListaService miListaService;

    private Perfil perfil;
    private Pelicula pelicula;

    @BeforeEach
    void setUp() {
        perfil = new Perfil();
        perfil.setId(1);
        perfil.setNombre("Jorge");

        pelicula = new Pelicula();
        pelicula.setId(10);
        pelicula.setTitulo("Inception");
    }

    @Test
    void estaEnMiLista_devuelveTrue_cuandoExiste() {
        when(miListaRepository.existsByPerfilIdAndPeliculaId(1, 10)).thenReturn(true);

        assertTrue(miListaService.estaEnMiLista(1, 10));
    }

    @Test
    void estaEnMiLista_devuelveFalse_cuandoNoExiste() {
        when(miListaRepository.existsByPerfilIdAndPeliculaId(1, 10)).thenReturn(false);

        assertFalse(miListaService.estaEnMiLista(1, 10));
    }

    @Test
    void agregar_guardaNuevaEntrada_cuandoNoExiste() {
        when(miListaRepository.findByPerfilIdAndPeliculaId(1, 10)).thenReturn(Optional.empty());
        MiLista guardada = new MiLista();
        when(miListaRepository.save(any(MiLista.class))).thenReturn(guardada);

        MiLista resultado = miListaService.agregar(perfil, pelicula);

        assertNotNull(resultado);
        verify(miListaRepository).save(any(MiLista.class));
    }

    @Test
    void agregar_noGuardaDuplicado_cuandoYaExiste() {
        MiLista existente = new MiLista();
        when(miListaRepository.findByPerfilIdAndPeliculaId(1, 10)).thenReturn(Optional.of(existente));

        MiLista resultado = miListaService.agregar(perfil, pelicula);

        assertSame(existente, resultado);
        verify(miListaRepository, never()).save(any());
    }

    @Test
    void obtenerListaPorPerfil_devuelveLista() {
        List<MiLista> lista = List.of(new MiLista(), new MiLista());
        when(miListaRepository.findByPerfilIdOrderByFechaAgregadoDesc(1)).thenReturn(lista);

        List<MiLista> resultado = miListaService.obtenerListaPorPerfil(1);

        assertNotNull(resultado);
        assertTrue(resultado.size() == 2);
    }
}
