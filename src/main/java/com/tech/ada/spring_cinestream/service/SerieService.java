package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.mapping.FilmeFavoritoMapper;
import com.tech.ada.spring_cinestream.dto.mapping.SerieFavoritaMapper;
import com.tech.ada.spring_cinestream.dto.request.FilmeFavoritoRequest;
import com.tech.ada.spring_cinestream.dto.request.SerieFavoritaRequest;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import com.tech.ada.spring_cinestream.model.SerieFavorita;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.SerieFavoritaRepository;
import org.springframework.stereotype.Service;

@Service
public class SerieService {

    private final SerieFavoritaRepository serieFavoritaRepository;
    private final ApiClient tmdbClient;
    private final UsuarioService usuarioService;

    public SerieService(SerieFavoritaRepository serieFavoritaRepository, ApiClient tmdbClient, UsuarioService usuarioService) {
        this.serieFavoritaRepository = serieFavoritaRepository;
        this.tmdbClient = tmdbClient;
        this.usuarioService = usuarioService;
    }

    public Page<TmdbSerie> buscarSeriePorTitulo(String titulo, Integer page) {
        return tmdbClient.buscarSeriesPorTitulo(titulo, page);
    }

    public void adicionarSerieFavorita(SerieFavoritaRequest serieFavoritaRequest) throws NotFoundException {
        Usuario usuario = usuarioService.buscarPorId(serieFavoritaRequest.getIdUsuario());
        SerieFavoritaMapper mapper = new SerieFavoritaMapper();
        SerieFavorita serieFavorita = mapper.toEntity(serieFavoritaRequest, usuario);
        serieFavoritaRepository.save(serieFavorita);
    }
}
