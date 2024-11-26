package com.tech.ada.spring_cinestream.controller;

import com.tech.ada.spring_cinestream.dto.response.UsuarioResponse;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorEmail(@PathVariable String email) throws NotFoundException {
        UsuarioResponse emailUsuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(emailUsuario);
    }
}
