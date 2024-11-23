package com.tech.ada.spring_cinestream.dto.mapping;

import com.tech.ada.spring_cinestream.dto.request.FilmeFavoritoRequest;
import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import com.tech.ada.spring_cinestream.model.Usuario;

public class FilmeFavoritoMapper {

    public FilmeFavorito toEntity(FilmeFavoritoRequest dto, Usuario usuario) {
        FilmeFavorito filmeFavorito = new FilmeFavorito();
        filmeFavorito.setTmdbId(dto.getIdFilme());
        filmeFavorito.setUsuario(usuario);
        return filmeFavorito;
    }
}
