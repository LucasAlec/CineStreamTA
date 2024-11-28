package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.mapping.UsuarioMapper;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.exception.AlreadyExistsException;
import com.tech.ada.spring_cinestream.exception.AlreadyFavouriteException;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApiClient tmdbClient;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, BCryptPasswordEncoder passwordEncoder, ApiClient tmdbClient) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.tmdbClient = tmdbClient;
    }

    @Transactional
    public Usuario criar(UsuarioRequest usuarioRequest) throws AlreadyExistsException {
        if (usuarioRepository.existsUsuarioByEmail(usuarioRequest.getEmail())) {
            throw new AlreadyExistsException(String.format(
                    "E-mail '%s' já cadastrado",
                    usuarioRequest.getEmail()
            ));
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) throws NotFoundException {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(
                        () -> new NotFoundException(String.format("Email %s não encontrado", email)
                ));
    }

    public Usuario buscarPorId(Long id) throws NotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Id %s não encontrado", id
                )));
    }

    public Optional<Usuario> validateUserLogin(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent() && passwordEncoder.matches(senha, usuario.get().getSenha())) {
            return usuario;
        }
        return Optional.empty();
    }

    public Usuario buscarUsuarioPorEmail(String email) throws NotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email " + email + " não encontrado"));
    }

    @Transactional
    public void favoritarFilme(Long id, Usuario usuario) throws AlreadyFavouriteException {
        TmdbFilme tmdbFilme = tmdbClient.buscarDetalhesFilme(id);
        if (usuario.filmeJaEFavorito(id)) throw new AlreadyFavouriteException();
        usuario.addFilmeFavorito(tmdbFilme);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void removerFilmeFavorito(Long id, Usuario usuario) throws NotFoundException {
        if (!usuario.filmeJaEFavorito(id)) throw new NotFoundException();
        usuario.removeFilmeFavorito(id);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void favoritarSerie(Long id, Usuario usuario) throws AlreadyFavouriteException {
        TmdbSerie tmdbSerie = tmdbClient.buscarDetalhesSerie(id);
        if (usuario.serieJaEFavorita(id)) throw new AlreadyFavouriteException();
        usuario.addSerieFavorita(tmdbSerie);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void removerSerieFavorita(Long id, Usuario usuario) throws NotFoundException {
        if (!usuario.serieJaEFavorita(id)) throw new NotFoundException();
        usuario.removeSerieFavorita(id);
        usuarioRepository.save(usuario);
    }
}
