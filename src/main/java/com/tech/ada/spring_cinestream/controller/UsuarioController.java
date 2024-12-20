package com.tech.ada.spring_cinestream.controller;

import com.tech.ada.spring_cinestream.exception.AlreadyFavouriteException;
import com.tech.ada.spring_cinestream.exception.ApiClientException;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarPorEmail(email));
    }

    @PostMapping("/favorito/filme/adicionar")
    public ResponseEntity<?> adicionarFilmeFavorito(@RequestBody Long idTmdb) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            usuarioService.favoritarFilme(idTmdb, usuario);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Filme adicionado como favorito.");
        } catch (ApiClientException apiEx) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format("Falha ao adicionar filme: %s", apiEx.getMessage()));
        } catch (AlreadyFavouriteException e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED )
                    .body("Esse filme já está na lista de favorito do usuário.");
        }
    }

    @PostMapping("/favorito/filme/remover")
    public ResponseEntity<?> removerFilmeFavorito(@RequestBody Long idTmdb) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            usuarioService.removerFilmeFavorito(idTmdb, usuario);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Filme removido da lista de favoritos.");
        } catch (ApiClientException apiEx) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format("Falha ao remover filme: %s", apiEx.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND )
                    .body("O filme não pertencia a lista de favoritos.");
        }
    }

    @PostMapping("/favorito/serie/adicionar")
    public ResponseEntity<?> adicionarSerieFavorita(@RequestBody Long idTmdb) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            usuarioService.favoritarSerie(idTmdb, usuario);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Série adicionada como favorita.");
        } catch (ApiClientException apiEx) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format("Falha ao adicionar série: %s", apiEx.getMessage()));
        } catch (AlreadyFavouriteException e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED )
                    .body("Esse filme já está na lista de favorito do usuário.");
        }
    }

    @PostMapping("/favorito/serie/remover")
    public ResponseEntity<?> removerSerieFavorita(@RequestBody Long idTmdb) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            usuarioService.removerSerieFavorita(idTmdb, usuario);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Série removida da lista de favoritos.");
        } catch (ApiClientException apiEx) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format("Falha ao remover série: %s", apiEx.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND )
                    .body("A Série não pertencia a lista de favoritos.");
        }
    }
}
