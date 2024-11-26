package com.tech.ada.spring_cinestream.dto.mapping;

import com.tech.ada.spring_cinestream.dto.request.FilmeFavoritoRequest;
import com.tech.ada.spring_cinestream.model.FilmesFavoritos;
import com.tech.ada.spring_cinestream.model.Usuario;

public class FilmeFavoritoMapper {

    public FilmesFavoritos toEntity(FilmeFavoritoRequest dto, Usuario usuario) {
        FilmesFavoritos filmeFavorito = new FilmesFavoritos();
        filmeFavorito.setTmdbId(dto.getIdFilme());
        filmeFavorito.setUsuario(usuario);
        return filmeFavorito;
    }
}
