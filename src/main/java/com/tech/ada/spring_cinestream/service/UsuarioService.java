package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.mapping.UsuarioMapper;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.dto.response.UsuarioResponse;
import com.tech.ada.spring_cinestream.exception.AlreadyExistsException;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import com.tech.ada.spring_cinestream.model.SerieFavorita;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.FilmeFavoritoRepository;
import com.tech.ada.spring_cinestream.repository.SerieFavoritaRepository;
import com.tech.ada.spring_cinestream.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final ApiClient apiClient;
    private final FilmeFavoritoRepository filmeFavoritoRepository;
    private final SerieFavoritaRepository serieFavoritaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ApiClient apiClient, FilmeFavoritoRepository filmeFavoritoRepository, SerieFavoritaRepository serieFavoritaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.apiClient = apiClient;
        this.filmeFavoritoRepository = filmeFavoritoRepository;
        this.serieFavoritaRepository = serieFavoritaRepository;
        this.usuarioMapper = new UsuarioMapper();
    }

    public UsuarioResponse criar(UsuarioRequest usuarioRequest) throws AlreadyExistsException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(usuarioRequest.getEmail());
        if (optionalUsuario.isPresent()) {
            throw new AlreadyExistsException(String.format(
                    "E-mail '%s' já cadastrado",
                    usuarioRequest.getEmail()
            ));
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioSalvo, Collections.emptyList(), Collections.emptyList());
    }

    public UsuarioResponse buscarPorEmail(String email) throws NotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Email %s não encontrado", email
                )));

        List<TmdbFilme> filmesFavoritos = filmeFavoritoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(filmeFavorito -> apiClient.buscarDetalhesFilme(filmeFavorito.getTmdbId()))
                .toList();

        List<TmdbSerie> seriesFavoritas = serieFavoritaRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(serieFavorita -> apiClient.buscarDetalhesSerie(serieFavorita.getTmdbId()))
                .toList();

        return usuarioMapper.toDTO(usuario, filmesFavoritos, seriesFavoritas);
    }

    public Usuario buscarPorId(Long id) throws NotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Id %s não encontrado", id
                )));
    }

    public Usuario buscarUsuarioPorEmail(String email) throws NotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email " + email + " não encontrado"));
    }



}
