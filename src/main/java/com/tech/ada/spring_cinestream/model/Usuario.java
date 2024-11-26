package com.tech.ada.spring_cinestream.model;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String username;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FilmeFavorito> filmesFavoritos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SerieFavorita> seriesFavoritas;

    public Usuario() {}

    public Usuario(String nome, String username, String email, String senha) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {this.username = username;}

    public List<TmdbSerie> getSeriesFavoritas() {
        return seriesFavoritas.stream().map(SerieFavorita::getTmdbSerie).toList();
    }

    public void setSeriesFavoritas(List<SerieFavorita> seriesFavoritas) {
        this.seriesFavoritas = seriesFavoritas;
    }

    public List<TmdbFilme> getFilmesFavoritos() {
        return filmesFavoritos.stream().map(FilmeFavorito::getTmdbFilme).toList();
    }

    public void setFilmesFavoritos(List<FilmeFavorito> filmesFavoritos) {this.filmesFavoritos = filmesFavoritos;}
}