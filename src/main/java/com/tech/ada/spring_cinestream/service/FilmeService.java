package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import org.springframework.stereotype.Service;


@Service
public class FilmeService {
    private final ApiClient tmdbClient;

    public FilmeService(ApiClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public Page<TmdbFilme> buscarFilmePorTitulo(String titulo, Integer page) {
        return tmdbClient.buscarFilmesPorTitulo(titulo, page);
    }
}
