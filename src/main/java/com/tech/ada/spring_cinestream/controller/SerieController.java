package com.tech.ada.spring_cinestream.controller;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.request.FilmeFavoritoRequest;
import com.tech.ada.spring_cinestream.dto.request.SerieFavoritaRequest;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.service.SerieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/series")
public class SerieController {
    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping
    public Page<TmdbSerie> buscarPorTitulo(
            @RequestParam String titulo,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        return serieService.buscarSeriePorTitulo(titulo, page);
    }
}
