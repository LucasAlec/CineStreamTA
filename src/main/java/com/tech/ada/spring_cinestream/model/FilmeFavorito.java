package com.tech.ada.spring_cinestream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import jakarta.persistence.*;

@Entity
public class FilmeFavorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "tmdb_id"))
    })
    private TmdbFilme tmdbFilme;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public FilmeFavorito() {}

    public FilmeFavorito(TmdbFilme tmdbFilme, Usuario usuario) {
        this.tmdbFilme = tmdbFilme;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public Long getTmdbId() {
        return tmdbFilme.getId();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTmdbFilme(TmdbFilme tmdbFilme) {
        this.tmdbFilme = tmdbFilme;
    }

    public TmdbFilme getTmdbFilme() {
        return tmdbFilme;
    }
}
