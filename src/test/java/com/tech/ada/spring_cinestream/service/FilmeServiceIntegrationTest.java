package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
public class FilmeServiceIntegrationTest {

    @MockBean
    private ApiClient apiClient;

    @Autowired
    FilmeService filmeService;

    @Test
    void testBuscarFilmePorTituloIntegracao() {
        // Dado
        TmdbFilme filmeMock = new TmdbFilme();
        filmeMock.setTitle("Gru - O Maldisposto 4 ");

        Page<TmdbFilme> mockedPage = new Page<>();
        mockedPage.setResults(List.of(filmeMock));
        when(apiClient.buscarFilmesPorTitulo("Gru - O Maldisposto 4 ", 1)).thenReturn(mockedPage);

        // Quando
        Page<TmdbFilme> result = filmeService.buscarFilmePorTitulo("Gru - O Maldisposto 4 ", 1);

        // Então
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getResults().isEmpty());
        Assertions.assertEquals("Gru - O Maldisposto 4 ", result.getResults().get(0).getTitle());
    }
}
