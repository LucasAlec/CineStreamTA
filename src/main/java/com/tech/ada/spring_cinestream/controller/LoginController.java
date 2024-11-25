package com.tech.ada.spring_cinestream.controller;

import com.tech.ada.spring_cinestream.dto.request.LoginRequest;
import com.tech.ada.spring_cinestream.dto.response.LoginResponse;
import com.tech.ada.spring_cinestream.exception.AuthenticationFailedException;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.service.JWTService;
import com.tech.ada.spring_cinestream.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {

    private final UsuarioService usuarioService;
    private final JWTService jwtService;

    public LoginController(UsuarioService usuarioService, JWTService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequestDTO) {
        try {
            Optional<Usuario> usuario = usuarioService.validateUserLogin(loginRequestDTO.login(), loginRequestDTO.password());
            if (usuario.isEmpty()) throw new AuthenticationFailedException("Usuário ou senha incorretos");

            String token = jwtService.generateToken(usuario.get());
            return ResponseEntity.ok(new LoginResponse(token, usuario.get().getEmail()));

        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
