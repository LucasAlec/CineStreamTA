package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SerieServiceTest {
    @Mock
    ApiClient tmdbClient;

    @InjectMocks
    SerieService serieService;

    @Test
    void testBuscarSeriePorTituloComResultados() {
        // Dado
        String titulo = "Breaking Bad";
        int page = 1;
        TmdbSerie mockedSerie = new TmdbSerie();
        mockedSerie.setName("Breaking Bad");

        Page<TmdbSerie> mockedPage = new Page<>();
        mockedPage.setResults(List.of(mockedSerie));

        when(tmdbClient.buscarSeriesPorTitulo(titulo, page)).thenReturn(mockedPage);

        // Quando
        Page<TmdbSerie> result = serieService.buscarSeriePorTitulo(titulo, page);

        // Então
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getResults().size());
        Assertions.assertEquals("Breaking Bad", result.getResults().get(0).getName());
        verify(tmdbClient, times(1)).buscarSeriesPorTitulo(titulo, page);
    }

    @Test
    void testBuscarSeriePorTituloSemResultados() {
        // Dado
        String titulo = "SerieInexistente";
        int page = 1;
        Page<TmdbSerie> mockedPage = new Page<>();
        mockedPage.setResults(List.of());

        when(tmdbClient.buscarSeriesPorTitulo(titulo, page)).thenReturn(mockedPage);

        // Quando
        Page<TmdbSerie> result = serieService.buscarSeriePorTitulo(titulo, page);

        // Então
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResults().isEmpty());
        verify(tmdbClient, times(1)).buscarSeriesPorTitulo(titulo, page);
    }

    @Test
    void testBuscarSeriePorTituloComErroNaApi() {
        // Dado
        String titulo = "ErrorSerie";
        int page = 1;

        when(tmdbClient.buscarSeriesPorTitulo(titulo, page))
                .thenThrow(new RuntimeException("Erro na API"));

        // Quando e Então
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            serieService.buscarSeriePorTitulo(titulo, page);
        });

        Assertions.assertEquals("Erro na API", exception.getMessage());
        verify(tmdbClient, times(1)).buscarSeriesPorTitulo(titulo, page);
    }

    @Test
    void testBuscarSeriePorTituloVerificarParametros() {
        // Dado
        String titulo = "Stranger Things";
        int page = 2;

        Page<TmdbSerie> mockedPage = new Page<>();
        mockedPage.setResults(List.of(new TmdbSerie()));

        when(tmdbClient.buscarSeriesPorTitulo(titulo, page)).thenReturn(mockedPage);

        // Quando
        serieService.buscarSeriePorTitulo(titulo, page);

        // Então
        verify(tmdbClient, times(1)).buscarSeriesPorTitulo(titulo, page);
    }
}
