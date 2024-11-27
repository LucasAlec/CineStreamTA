package com.tech.ada.spring_cinestream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import jakarta.persistence.*;

@Entity
public class SerieFavorita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "tmdb_id"))
    })
    private TmdbSerie tmdbSerie;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    public SerieFavorita() {}

    public SerieFavorita(TmdbSerie tmdbSerie) {
        this.tmdbSerie = tmdbSerie;
    }

    public Long getId() {
        return id;
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

    public void setTmdbSerie(TmdbSerie tmdbSerie) {
        this.tmdbSerie = tmdbSerie;
    }

    public TmdbSerie getTmdbSerie() {
        return tmdbSerie;
    }
}
