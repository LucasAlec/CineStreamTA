package com.tech.ada.spring_cinestream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.service.SerieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class SerieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    SerieService serieService;

    @Test
    public void dadoTituloDeSerie_quandoBuscarPorTitulo_entaoRetornaListaDeSeries() throws Exception {
        // Dado
        String titulo = "Stranger Things";
        Integer page = 1;
        TmdbSerie serie1 = new TmdbSerie(Arrays.asList(1, 2), 1L, "Stranger Things", "Sinopse de Stranger Things", "2016-07-15", 8.7, 2000, "/poster1.jpg");
        TmdbSerie serie2 = new TmdbSerie(Arrays.asList(1, 3), 2L, "Stranger Things 2", "Sinopse de Stranger Things 2", "2017-10-27", 8.5, 1800, "/poster2.jpg");
        Page<TmdbSerie> paginaDeSeries = new Page<>(1, Arrays.asList(serie1, serie2), 1, 10);

        when(serieService.buscarSeriePorTitulo(titulo, page)).thenReturn(paginaDeSeries);

        // Quando
        mockMvc.perform(get("/api/series")
                        .param("titulo", titulo)
                        .param("page", page.toString()))
                // Então
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name").value("Stranger Things"))
                .andExpect(jsonPath("$.results[1].name").value("Stranger Things 2"))
                .andExpect(jsonPath("$.results[0].id").value(1))
                .andExpect(jsonPath("$.results[1].id").value(2));
    }

    @Test
    public void dadoTituloAusente_quandoBuscarPorTitulo_entaoRetornaBadRequest() throws Exception {
        // Quando
        mockMvc.perform(get("/api/series")
                        .param("page", "1"))
                // Então
                .andExpect(status().isBadRequest());
    }


    @Test
    public void dadoTituloInexistente_quandoBuscarPorTitulo_entaoRetornaListaVazia() throws Exception {
        // Dado
        String titulo = "NonExistentSeries";
        Integer page = 1;
        Page<TmdbSerie> paginaDeSeriesVazia = new Page<>(1, Arrays.asList(), 1, 0);

        when(serieService.buscarSeriePorTitulo(titulo, page)).thenReturn(paginaDeSeriesVazia);

        // Quando
        mockMvc.perform(get("/api/series")
                        .param("titulo", titulo)
                        .param("page", page.toString()))
                // Então
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isEmpty())
                .andExpect(jsonPath("$.total_pages").value(1))
                .andExpect(jsonPath("$.total_results").value(0));
    }

}
