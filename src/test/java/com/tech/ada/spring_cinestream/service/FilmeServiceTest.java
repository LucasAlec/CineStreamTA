package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmeServiceTest {

    @Mock
    ApiClient tmdbClient;

    @InjectMocks
    FilmeService filmeService;


    @Test
    void testBuscarFilmePorTituloComResultados() {
        // Dado
        String titulo = "Deadpool & Wolverine";
        int page = 1;
        TmdbFilme mockedFilme = new TmdbFilme();
        mockedFilme.setTitle("Deadpool & Wolverine");

        Page<TmdbFilme> mockedPage = new Page<>();
        mockedPage.setResults(List.of(mockedFilme));

        when(tmdbClient.buscarFilmesPorTitulo(titulo, page)).thenReturn(mockedPage);

        // Quando
        Page<TmdbFilme> result = filmeService.buscarFilmePorTitulo(titulo, page);

        // Então
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getResults().size());
        Assertions.assertEquals("Deadpool & Wolverine", result.getResults().get(0).getTitle());
        verify(tmdbClient, times(1)).buscarFilmesPorTitulo(titulo, page);
    }

    @Test
    void testBuscarFilmePorTituloSemResultados() {
        // Dado
        String titulo = "FilmeInexistente";
        int page = 1;
        Page<TmdbFilme> mockedPage = new Page<>();
        mockedPage.setResults(List.of());

        when(tmdbClient.buscarFilmesPorTitulo(titulo, page)).thenReturn(mockedPage);

        // Quando
        Page<TmdbFilme> result = filmeService.buscarFilmePorTitulo(titulo, page);

        // Então
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResults().isEmpty());
        verify(tmdbClient, times(1)).buscarFilmesPorTitulo(titulo, page);
    }

    @Test
    void testBuscarFilmePorTituloComErroNaApi() {
        // Dado
        String titulo = "ErrorFilme";
        int page = 1;

        when(tmdbClient.buscarFilmesPorTitulo(titulo, page))
                .thenThrow(new RuntimeException("Erro na API"));

        // Quando e Então
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            filmeService.buscarFilmePorTitulo(titulo, page);
        });

        Assertions.assertEquals("Erro na API", exception.getMessage());
        verify(tmdbClient, times(1)).buscarFilmesPorTitulo(titulo, page);
    }


    @Test
    void testBuscarFilmePorTituloVerificarParametros() {
        // Dado
        String titulo = "Avatar";
        int page = 2;

        Page<TmdbFilme> mockedPage = new Page<>();
        mockedPage.setResults(List.of(new TmdbFilme()));

        when(tmdbClient.buscarFilmesPorTitulo(titulo, page)).thenReturn(mockedPage);

        // Quando
        filmeService.buscarFilmePorTitulo(titulo, page);

        // Então
        verify(tmdbClient, times(1)).buscarFilmesPorTitulo(titulo, page);
    }

    @Test
    void testBuscarFilmePorTituloComPaginaNula() {
        // Dado
        String titulo = "Spider-Man";
        Integer page = null;
        int defaultPage = 1;

        TmdbFilme mockedFilme = new TmdbFilme();
        mockedFilme.setTitle("Spider-Man");

        Page<TmdbFilme> mockedPage = new Page<>();
        mockedPage.setResults(List.of(mockedFilme));

        when(tmdbClient.buscarFilmesPorTitulo(titulo, defaultPage)).thenReturn(mockedPage);

        // Quando
        Page<TmdbFilme> result = filmeService.buscarFilmePorTitulo(titulo, defaultPage);

        // Então
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getResults().size());
        Assertions.assertEquals("Spider-Man", result.getResults().get(0).getTitle());
        verify(tmdbClient, times(1)).buscarFilmesPorTitulo(titulo, defaultPage);
    }


}
