package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import org.springframework.stereotype.Service;

@Service
public class SerieService {

    private final ApiClient tmdbClient;

    public SerieService(ApiClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public Page<TmdbSerie> buscarSeriePorTitulo(String titulo, Integer page) {
        return tmdbClient.buscarSeriesPorTitulo(titulo, page);
    }
}
