package com.tech.ada.spring_cinestream.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.tech.ada.spring_cinestream.exception.AuthenticationFailedException;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.TokenRepository;
import com.tech.ada.spring_cinestream.model.TokenEntity;
import com.tech.ada.spring_cinestream.dto.token.TokenResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class JWTService {

    private final TokenRepository tokenRepository;
    private final String ISSUER = "CineStream";
    private final Algorithm algorithm;

    public JWTService(@Value("${jwt.secret}") String secretKey, TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public Optional<TokenResult> getToken(String token) {
        Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByToken(token);

        if (tokenEntityOptional.isPresent()) {
            TokenEntity tokenEntity = tokenEntityOptional.get();
            return Optional.of(new TokenResult(tokenEntity, isExpired(tokenEntity)));
        }

        return Optional.empty();
    }

    public boolean isExpired(TokenEntity tokenEntity) {
        return tokenEntity.getExpirationDate().isBefore(Instant.now());
    }

    public String generateToken(Usuario user) throws AuthenticationFailedException {
        try {
            Instant expirationDate = generateExpirationDate();
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);

            TokenEntity tokenEntity = new TokenEntity(token, expirationDate, user);
            tokenRepository.saveAndFlush(tokenEntity);
            return token;
        } catch (JWTCreationException e) {
            throw new AuthenticationFailedException("Erro durante a autenticação.");
        }
    }

    public Optional<String> validateToken(String token) {
        try {
            Optional<TokenResult> tokenResultOpt = getToken(token);
            if (tokenResultOpt.isPresent()) {
                TokenResult tokenResult = tokenResultOpt.get();
                if (tokenResult.isExpired()) return Optional.empty();
                return Optional.ofNullable(JWT.require(algorithm)
                        .withIssuer(ISSUER)
                        .build()
                        .verify(token)
                        .getSubject());
            }

            return Optional.empty();
        } catch (JWTCreationException e) {
            return Optional.empty();
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC);
    }
}
