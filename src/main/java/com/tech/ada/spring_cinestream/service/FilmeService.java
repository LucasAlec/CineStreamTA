package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.dto.mapping.FilmeFavoritoMapper;
import com.tech.ada.spring_cinestream.dto.request.FilmeFavoritoRequest;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.FilmeFavoritoRepository;
import org.springframework.stereotype.Service;


@Service
public class FilmeService {
    private final FilmeFavoritoRepository filmeFavoritoRepository;
    private final UsuarioService usuarioService;
    private final ApiClient tmdbClient;

    public FilmeService(FilmeFavoritoRepository filmeFavoritoRepository, UsuarioService usuarioService, ApiClient tmdbClient) {
        this.filmeFavoritoRepository = filmeFavoritoRepository;
        this.usuarioService = usuarioService;
        this.tmdbClient = tmdbClient;
    }

    public Page<TmdbFilme> buscarFilmePorTitulo(String titulo, Integer page) {
        return tmdbClient.buscarFilmesPorTitulo(titulo, page);
    }

    public void adicionarFilmeFavorito(FilmeFavoritoRequest filmeFavoritoRequest) throws NotFoundException {
        Usuario usuario = usuarioService.buscarPorId(filmeFavoritoRequest.getIdUsuario());

        FilmeFavoritoMapper mapper = new FilmeFavoritoMapper();
        FilmeFavorito filmeFavorito = mapper.toEntity(filmeFavoritoRequest, usuario);

        filmeFavoritoRepository.save(filmeFavorito);
    }
}
