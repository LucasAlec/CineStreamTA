package com.tech.ada.spring_cinestream.model;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Instant expirationDate;

    @ManyToOne
    private Usuario usuario;


    public TokenEntity() {
    }

    public TokenEntity(String token, Instant expirationDate, Usuario usuario) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
