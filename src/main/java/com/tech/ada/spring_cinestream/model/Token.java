package com.tech.ada.spring_cinestream.model;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Instant expirationDate;

    @ManyToOne
    private Usuario usuario;


    public Token() {
    }

    public Token(String token, Instant expirationDate, Usuario usuario) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
