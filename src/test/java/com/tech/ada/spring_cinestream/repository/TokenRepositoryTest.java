package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.Token;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    private Token token;

    @BeforeEach
    public void setup() {
        token = new Token();
        token.setToken("12345abcde");
        tokenRepository.save(token);
    }

    @Test
    @Order(1)
    public void tokenJaCadastrado_encontrarPorToken_deveRetornarToken() {
        String tokenValor = "12345abcde";

        Optional<Token> encontrado = tokenRepository.findByToken(tokenValor);

        Assertions.assertTrue(encontrado.isPresent(), "Token deveria ser encontrado");
        Assertions.assertEquals(tokenValor, encontrado.get().getToken(), "Os tokens não são iguais");
    }

    @Test
    @Order(2)
    public void tokenNaoCadastrado_encontrarPorToken_deveRetornarVazio() {
        Optional<Token> encontrado = tokenRepository.findByToken("naoexiste");

        Assertions.assertTrue(encontrado.isEmpty(), "Esperava-se que o token não fosse encontrado, mas o retorno não foi vazio.");
    }


    @Test
    @Order(3)
    public void tokenJaCadastrado_existePorToken_deveRetornarTrue() {
        String tokenValor = "12345abcde";

        boolean existe = tokenRepository.existsById(token.getId());

        Assertions.assertTrue(existe, "O token deveria existir com o valor informado.");
    }

    @Test
    @Order(4)
    public void tokenNaoCadastrado_existePorToken_deveRetornarFalse() {
        boolean existe = tokenRepository.existsById(999L);

        Assertions.assertFalse(existe, "O token não deveria existir com o ID informado.");
    }


    @AfterEach
    public void tearDown() {
        tokenRepository.delete(token);
    }



}
