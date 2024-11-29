package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomUserDetailsServiceTest {
    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testCarregarUsuarioPorUsername_Sucesso() {
        // Dado
        String email = "viviane@gmail.com";
        String senha = "senha123";
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail(email);
        usuarioMock.setSenha(senha);

        // Quando
        try {
            when(usuarioService.buscarUsuarioPorEmail(email)).thenReturn(usuarioMock);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        // Então
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(email, userDetails.getUsername());
        Assertions.assertEquals(senha, userDetails.getPassword());
    }

    @Test
    void testCarregarUsuarioPorUsername_UsuarioNaoEncontrado() {
        // Dado
        String email = "mariana@gmail.com";

        // Quando
        try {
            when(usuarioService.buscarUsuarioPorEmail(email)).thenThrow(new NotFoundException("Usuário não encontrado"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        // Então
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }


}
