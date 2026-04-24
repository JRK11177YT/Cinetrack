package com.cinetrack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cinetrack.model.Pelicula;
import com.cinetrack.repository.PeliculaRepository;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

    @Mock
    private PeliculaRepository peliculaRepository;

    @InjectMocks
    private PeliculaService peliculaService;

    @Test
    void obtenerPorId_devuelvePelicula_cuandoExiste() {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(1);
        pelicula.setTitulo("The Godfather");
        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula));

        Optional<Pelicula> resultado = peliculaService.obtenerPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("The Godfather", resultado.get().getTitulo());
    }

    @Test
    void obtenerPorId_devuelveVacio_cuandoNoExiste() {
        when(peliculaRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Pelicula> resultado = peliculaService.obtenerPorId(999);

        assertFalse(resultado.isPresent());
    }

    @Test
    void obtenerDestacadas_delegaEnRepositorio() {
        List<Pelicula> destacadas = List.of(new Pelicula(), new Pelicula());
        when(peliculaRepository.findByDestacadaTrue()).thenReturn(destacadas);

        List<Pelicula> resultado = peliculaService.obtenerDestacadas();

        assertEquals(2, resultado.size());
        verify(peliculaRepository).findByDestacadaTrue();
    }

    @Test
    void buscarPorTitulo_devuelveResultados_casInsensitive() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Inception");
        when(peliculaRepository.findByTituloContainingIgnoreCase("incep")).thenReturn(List.of(pelicula));

        List<Pelicula> resultado = peliculaService.buscarPorTitulo("incep");

        assertFalse(resultado.isEmpty());
        assertEquals("Inception", resultado.get(0).getTitulo());
    }

    @Test
    void contar_delegaEnRepositorio() {
        when(peliculaRepository.count()).thenReturn(31L);

        assertEquals(31L, peliculaService.contar());
    }

    @Test
    void eliminar_llamaDeleteById() {
        peliculaService.eliminar(5);

        verify(peliculaRepository).deleteById(5);
    }
}
