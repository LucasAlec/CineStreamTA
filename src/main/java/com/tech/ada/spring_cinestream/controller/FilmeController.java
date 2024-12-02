package com.tech.ada.spring_cinestream.controller;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.dto.request.FilmeFavoritoRequest;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.service.FilmeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {
    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    public Page<TmdbFilme> buscarPorTitulo(
            @RequestParam String titulo,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        return filmeService.buscarFilmePorTitulo(titulo, page);
    }
}