package com.tech.ada.spring_cinestream.dto.response;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;

import java.util.List;

public class UsuarioResponse {
    private Long id;
    private String nome;
    private String username;
    private String email;
    private List<TmdbFilme> filmesFavoritos;
    private List<TmdbSerie> seriesFavoritas;

    public UsuarioResponse () {
    }

    public UsuarioResponse(Long id, String nome, String username, String email, List<TmdbFilme> filmesFavoritos, List<TmdbSerie> seriesFavoritas) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.filmesFavoritos = filmesFavoritos;
        this.seriesFavoritas = seriesFavoritas;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TmdbFilme> getFilmesFavoritos() {
        return filmesFavoritos;
    }

    public void setFilmesFavoritos(List<TmdbFilme> filmesFavoritos) {
        this.filmesFavoritos = filmesFavoritos;
    }

    public List<TmdbSerie> getSeriesFavoritas() {
        return seriesFavoritas;
    }

    public void setSeriesFavoritas(List<TmdbSerie> seriesFavoritas) {
        this.seriesFavoritas = seriesFavoritas;
    }
}
