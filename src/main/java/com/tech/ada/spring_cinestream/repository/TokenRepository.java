package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
