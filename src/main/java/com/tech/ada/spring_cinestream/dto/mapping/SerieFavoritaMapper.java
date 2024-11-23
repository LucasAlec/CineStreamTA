package com.tech.ada.spring_cinestream.dto.mapping;

import com.tech.ada.spring_cinestream.dto.request.SerieFavoritaRequest;
import com.tech.ada.spring_cinestream.model.SerieFavorita;
import com.tech.ada.spring_cinestream.model.Usuario;

public class SerieFavoritaMapper {

    public SerieFavorita toEntity(SerieFavoritaRequest dto, Usuario usuario) {
        SerieFavorita serieFavorita = new SerieFavorita();
        serieFavorita.setTmdbId(dto.getIdSerie());
        serieFavorita.setUsuario(usuario);
        return serieFavorita;
    }
}
