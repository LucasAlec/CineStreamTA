package com.tech.ada.spring_cinestream.controller;

import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.dto.response.UsuarioResponse;
import com.tech.ada.spring_cinestream.exception.AlreadyExistsException;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest usuario) throws AlreadyExistsException {
        UsuarioResponse novoUsuario = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorEmail(@RequestParam String email) throws NotFoundException {
        UsuarioResponse emailUsuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(emailUsuario);
    }
}
