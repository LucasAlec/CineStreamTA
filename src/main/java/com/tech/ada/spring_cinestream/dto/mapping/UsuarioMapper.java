package com.tech.ada.spring_cinestream.dto.mapping;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.dto.response.UsuarioResponse;
import com.tech.ada.spring_cinestream.model.Usuario;

import java.util.List;


public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setNickName(dto.getNickname());
        usuario.setEmail(dto.getEmail());
        return usuario;
    }

    public UsuarioResponse toDTO(Usuario usuario, List<TmdbFilme> filmesFavoritos, List<TmdbSerie> seriesFavoritas) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setNickname(usuario.getNickName());
        dto.setEmail(usuario.getEmail());

        dto.setFilmesFavoritos(filmesFavoritos);
        dto.setSeriesFavoritas(seriesFavoritas);

        return dto;
    }
}
