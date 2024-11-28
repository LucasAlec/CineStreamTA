package com.tech.ada.spring_cinestream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String username;
    private String email;
    @JsonIgnore
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FilmeFavorito> filmesFavoritos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
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

//    public List<TmdbSerie> getSeriesFavoritas() {
//        return seriesFavoritas.stream().map(SerieFavorita::getTmdbSerie).toList();
//    }

    public List<TmdbSerie> getSeriesFavoritas() {
        if (seriesFavoritas == null) {
            return new ArrayList<>();
        }
        return seriesFavoritas.stream().map(SerieFavorita::getTmdbSerie).toList();
    }

    public void addFilmeFavorito(TmdbFilme tmdbFilme) {
        if (filmesFavoritos == null) {
            this.filmesFavoritos = new ArrayList<>();
        }
        filmesFavoritos.add(new FilmeFavorito(tmdbFilme, this));
    }

    public void addSerieFavorita(TmdbSerie tmdbSerie) {
        if (seriesFavoritas == null) {
            this.seriesFavoritas = new ArrayList<>();
        }
        seriesFavoritas.add(new SerieFavorita(tmdbSerie, this));
    }

    public void removeSerieFavorita(Long id) {
        SerieFavorita serieFavorita = seriesFavoritas.stream()
                .filter(serie ->
                        Objects.equals(serie.getTmdbId(), id))
                .findFirst()
                .orElse(null);

        seriesFavoritas.remove(serieFavorita);
    }

    public void removeFilmeFavorito(Long id) {
        FilmeFavorito filmeFavorito = filmesFavoritos.stream()
                .filter(filme ->
                        Objects.equals(filme.getTmdbId(), id))
                .findFirst()
                .orElse(null);

        filmesFavoritos.remove(filmeFavorito);
    }

    public void setSeriesFavoritas(List<SerieFavorita> seriesFavoritas) {
        this.seriesFavoritas = seriesFavoritas;
    }

//    public List<TmdbFilme> getFilmesFavoritos() {
//        return filmesFavoritos.stream().map(FilmeFavorito::getTmdbFilme).toList();
//    }

    public List<TmdbFilme> getFilmesFavoritos() {
        if (filmesFavoritos == null) {
            return new ArrayList<>();
        }
        return filmesFavoritos.stream().map(FilmeFavorito::getTmdbFilme).toList();
    }

    public void setFilmesFavoritos(List<FilmeFavorito> filmesFavoritos) {this.filmesFavoritos = filmesFavoritos;}

    public boolean filmeJaEFavorito(Long id) {
        return filmesFavoritos.stream()
                .anyMatch(filmeFavorito ->
                        Objects.equals(filmeFavorito.getTmdbFilme().getId(), id)
                );
    }

    public boolean serieJaEFavorita(Long id) {
        return seriesFavoritas.stream()
                .anyMatch(serieFavorita ->
                        Objects.equals(serieFavorita.getTmdbSerie().getId(), id)
                );
    }
}