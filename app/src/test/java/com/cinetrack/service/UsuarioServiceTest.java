package com.cinetrack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cinetrack.model.Usuario;
import com.cinetrack.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void registrar_cifraPasswordYGuardaUsuario() {
        when(passwordEncoder.encode("mipassword")).thenReturn("$2a$10$hash");
        Usuario guardado = new Usuario();
        guardado.setEmail("test@cinetrack.com");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);

        Usuario resultado = usuarioService.registrar("test@cinetrack.com", "mipassword", "BASICO");

        verify(passwordEncoder).encode("mipassword");
        verify(usuarioRepository).save(any(Usuario.class));
        assertEquals("test@cinetrack.com", resultado.getEmail());
    }

    @Test
    void buscarPorEmail_devuelveUsuario_cuandoExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("jorge@cinetrack.com");
        when(usuarioRepository.findByEmail("jorge@cinetrack.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorEmail("jorge@cinetrack.com");

        assertTrue(resultado.isPresent());
        assertEquals("jorge@cinetrack.com", resultado.get().getEmail());
    }

    @Test
    void buscarPorEmail_devuelveVacio_cuandoNoExiste() {
        when(usuarioRepository.findByEmail("noexiste@cinetrack.com")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorEmail("noexiste@cinetrack.com");

        assertFalse(resultado.isPresent());
    }

    @Test
    void contar_delegaEnRepositorio() {
        when(usuarioRepository.count()).thenReturn(42L);

        assertEquals(42L, usuarioService.contar());
    }
}
