package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senha123");
        usuario.setUsername("joao123");
        usuario.setNome("João da Silva");

        usuarioRepository.save(usuario);
    }

    @Test
    @Order(1)
    public void usuarioJaCadastrado_encontrarPorEmail_deveRetornarUsuario() {
        Optional<Usuario> encontrado = usuarioRepository.findByEmail("joao@email.com");

        Assertions.assertTrue(encontrado.isPresent(), "Usuário deveria ser encontrado");

        Assertions.assertEquals(usuario.getEmail(), encontrado.get().getEmail(), "Os e-mails não são iguais");
    }

    @Test
    @Order(2)
    public void usuarioNaoCadastrado_encontrarPorEmail_deveRetornarVazio() {
        Optional<Usuario> encontrado = usuarioRepository.findByEmail("naoexiste@email.com");

        Assertions.assertTrue(encontrado.isEmpty(), "O usuário não deveria ser encontrado");
    }

    @AfterEach
    public void tearDown() {
        usuarioRepository.deleteAll();
    }
}
