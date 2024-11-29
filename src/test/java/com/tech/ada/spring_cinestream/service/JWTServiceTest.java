package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.exception.AuthenticationFailedException;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.TokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import com.tech.ada.spring_cinestream.model.Token;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.when;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class JWTServiceTest {

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private JWTService jwtService;

    private String generateValidToken(String email) {
        String secret = "test-secret";
        return JWT.create()
                .withIssuer("CineStream")
                .withSubject(email)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(Algorithm.HMAC256(secret));
    }

    @Test
    void testGenerateToken_Success() {
        // Dado
        String email = "viviane@gmail.com";
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail(email);

        // Quando
        String token = jwtService.generateToken(usuarioMock);

        // Então
        Assertions.assertNotNull(token, "O token gerado não pode ser nulo.");
        Assertions.assertTrue(token.startsWith("eyJ"), "O token gerado deve começar com 'eyJ' (JWT codificado).");
    }

    @Test
    void testValidateToken_TokenExpired() {
        // Dado
        String email = "viviane@gmail.com";
        String token = generateValidToken(email);

        Token tokenEntityMock = new Token(token, Instant.now().minusSeconds(3600), new Usuario());
        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntityMock));

        // Quando
        Optional<String> userEmailOpt = jwtService.validateToken(token);

        // Então
        Assertions.assertTrue(userEmailOpt.isEmpty(), "O token expirado deve retornar um valor vazio.");
    }

    @Test
    void testValidateToken_TokenInvalid() {
        // Dado
        String invalidToken = "invalidToken";

        // Quando
        Optional<String> userEmailOpt = jwtService.validateToken(invalidToken);

        // Então
        Assertions.assertTrue(userEmailOpt.isEmpty(), "O token inválido deve retornar um valor vazio.");
    }
}
