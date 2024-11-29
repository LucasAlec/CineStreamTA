package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class SerieServiceIntegrationTest {

    @MockBean
    private ApiClient apiClient;

    @Autowired
    private SerieService serieService;


    @Test
    void testBuscarSeriePorTituloIntegracao() {
        // Dado
        TmdbSerie serieMock = new TmdbSerie();
        serieMock.setName("Breaking Bad");

        Page<TmdbSerie> mockedPage = new Page<>();
        mockedPage.setResults(List.of(serieMock));
        when(apiClient.buscarSeriesPorTitulo("Breaking Bad", 1)).thenReturn(mockedPage);

        // Quando
        Page<TmdbSerie> result = serieService.buscarSeriePorTitulo("Breaking Bad", 1);

        // Entao
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getResults().isEmpty());
        Assertions.assertEquals("Breaking Bad", result.getResults().get(0).getName());
    }
}
