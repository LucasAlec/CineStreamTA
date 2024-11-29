package com.tech.ada.spring_cinestream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.Page;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.service.FilmeService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmeControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private FilmeService filmeService;


    @Test
    public void dadoTituloDeFilme_quandoBuscarPorTitulo_entaoRetornaListaDeFilmes() throws Exception {
        // Dado
        String titulo = "Inception";
        Integer page = 1;
        TmdbFilme filme1 = new TmdbFilme("Inception", "Sinopse do filme", "2010-07-16", 8.8, "/poster1.jpg", 1L);
        TmdbFilme filme2 = new TmdbFilme("Interstellar", "Sinopse de Interstellar", "2014-11-07", 8.6, "/poster2.jpg", 2L);
        Page<TmdbFilme> paginaDeFilmes = new Page<>(1, Arrays.asList(filme1, filme2), 1, 10);

        when(filmeService.buscarFilmePorTitulo(titulo, page)).thenReturn(paginaDeFilmes);

        // Quando
        mockMvc.perform(get("/api/filmes")
                        .param("titulo", titulo)
                        .param("page", page.toString()))
                // Então
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].title").value("Inception"))
                .andExpect(jsonPath("$.results[1].title").value("Interstellar"))
                .andExpect(jsonPath("$.results[0].id").value(1))
                .andExpect(jsonPath("$.results[1].id").value(2));
    }

    @Test
    public void dadoTituloDeFilme_quandoBuscarPorTituloSemPage_entaoRetornaListaDeFilmesComPagina1() throws Exception {
        // Dado
        String titulo = "Inception";
        Integer page = 1;
        TmdbFilme filme1 = new TmdbFilme("Inception", "Sinopse do filme", "2010-07-16", 8.8, "/poster1.jpg", 1L);
        TmdbFilme filme2 = new TmdbFilme("Interstellar", "Sinopse de Interstellar", "2014-11-07", 8.6, "/poster2.jpg", 2L);
        Page<TmdbFilme> paginaDeFilmes = new Page<>(page, Arrays.asList(filme1, filme2), 1, 10);

        when(filmeService.buscarFilmePorTitulo(titulo, page)).thenReturn(paginaDeFilmes);

        // Quando
        mockMvc.perform(get("/api/filmes")
                        .param("titulo", titulo))
                // Então
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].title").value("Inception"))
                .andExpect(jsonPath("$.results[1].title").value("Interstellar"))
                .andExpect(jsonPath("$.results[0].id").value(1))
                .andExpect(jsonPath("$.results[1].id").value(2))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.total_pages").value(1));
    }

    @Test
    public void dadoTituloInexistente_quandoBuscarPorTitulo_entaoRetornaListaVazia() throws Exception {
        // Dado
        String titulo = "NonExistentMovie";
        Integer page = 1;
        Page<TmdbFilme> paginaDeFilmesVazia = new Page<>(1, Arrays.asList(), 1, 0);

        when(filmeService.buscarFilmePorTitulo(titulo, page)).thenReturn(paginaDeFilmesVazia);

        // Quando
        mockMvc.perform(get("/api/filmes")
                        .param("titulo", titulo)
                        .param("page", page.toString()))
                // Então
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isEmpty())
                .andExpect(jsonPath("$.total_pages").value(1))
                .andExpect(jsonPath("$.total_results").value(0));
    }

    @Test
    public void dadoTituloAusente_quandoBuscarPorTitulo_entaoRetornaBadRequest() throws Exception {
        // Quando
        mockMvc.perform(get("/api/filmes")
                        .param("page", "1"))
                // Então
                .andExpect(status().isBadRequest());
    }

    @Test
    public void dadoPageNaoNumerico_quandoBuscarPorTitulo_entaoRetornaBadRequest() throws Exception {
        // Dado
        String titulo = "Inception";

        // Quando
        mockMvc.perform(get("/api/filmes")
                        .param("titulo", titulo)
                        .param("page", "abc"))
                // Então
                .andExpect(status().isBadRequest());
    }
}
