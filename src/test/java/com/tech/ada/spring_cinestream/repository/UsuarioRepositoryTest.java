package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.Usuario;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setEmail("joao@gmail.com");
        usuario.setSenha("senha123");
        usuario.setUsername("joao123");
        usuario.setNome("João da Silva");

        usuarioRepository.save(usuario);
    }


    @Test
    @Order(1)
    public void quandoEmailNaoExistente_entaoDeveRetornarVazio() {
        Optional<Usuario> encontrado = usuarioRepository.findByEmail("naoexiste@gmail.com");

        Assertions.assertTrue(encontrado.isEmpty(), "Esperava-se que o usuário não fosse encontrado, mas o retorno não foi vazio.");
    }

    @Test
    @Order(2)
    public void usuarioJaCadastrado_existsPorEmail_deveRetornarTrue() {
        String email = "joao@gmail.com";
        boolean existe = usuarioRepository.existsUsuarioByEmail(email);

        Assertions.assertTrue(existe, "O usuário deveria existir com o e-mail informado.");
    }

    @Test
    @Order(3)
    public void quandoEmailNaoCadastrado_existsPorEmail_deveRetornarFalse() {
        String emailNaoCadastrado = "naoexiste@gmail.com";
        boolean existe = usuarioRepository.existsUsuarioByEmail(emailNaoCadastrado);

        Assertions.assertFalse(existe, "O usuário não deveria existir com o e-mail informado.");
    }

}
