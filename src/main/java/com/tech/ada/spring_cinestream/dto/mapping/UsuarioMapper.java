package com.tech.ada.spring_cinestream.dto.mapping;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.dto.response.UsuarioResponse;
import com.tech.ada.spring_cinestream.model.Usuario;

import java.util.Collections;
import java.util.List;


public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public UsuarioResponse toDTO(Usuario usuario, List<TmdbFilme> filmesFavoritos, List<TmdbSerie> seriesFavoritas) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());

        dto.setFilmesFavoritos(filmesFavoritos);
        dto.setSeriesFavoritas(seriesFavoritas);

        return dto;
    }

    public UsuarioResponse toDTO(Usuario usuario) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());

        dto.setFilmesFavoritos(Collections.emptyList());
        dto.setSeriesFavoritas(Collections.emptyList());

        return dto;
    }
}
