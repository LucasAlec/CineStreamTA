package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.mapping.UsuarioMapper;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.dto.response.UsuarioResponse;
import com.tech.ada.spring_cinestream.exception.AlreadyExistsException;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = new UsuarioMapper();
        this.passwordEncoder = new BCryptPasswordEncoder();
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
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioSalvo);
    }

    public UsuarioResponse buscarPorEmail(String email) throws NotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(
                        () -> new NotFoundException(String.format("Email %s não encontrado", email)
                ));

        return usuarioMapper.toDTO(usuario, usuario.getFilmesFavoritos(), usuario.getSeriesFavoritas());
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
}
