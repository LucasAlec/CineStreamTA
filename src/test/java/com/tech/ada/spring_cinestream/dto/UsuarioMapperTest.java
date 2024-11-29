package com.tech.ada.spring_cinestream.dto;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.mapping.UsuarioMapper;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.dto.response.UsuarioResponse;
import com.tech.ada.spring_cinestream.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioMapperTest {

    private UsuarioMapper usuarioMapper;

    @BeforeEach
    public void setup() {
        usuarioMapper = new UsuarioMapper();
    }

    @Test
    public void deveConverterUsuarioRequestParaUsuario() {
        // Dado
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Lucas");
        request.setUsername("lucas123");
        request.setEmail("lucas@gmail.com");
        request.setSenha("senha123");

        // Quando
        Usuario usuario = usuarioMapper.toEntity(request);

        // Então
        assertEquals("Lucas", usuario.getNome());
        assertEquals("lucas123", usuario.getUsername());
        assertEquals("lucas@gmail.com", usuario.getEmail());
        assertEquals("senha123", usuario.getSenha());
        assertTrue(usuario.getFilmesFavoritos().isEmpty(), "A lista de filmes favoritos deve estar vazia");
        assertTrue(usuario.getSeriesFavoritas().isEmpty(), "A lista de séries favoritas deve estar vazia");
    }

    @Test
    public void deveConverterUsuarioParaUsuarioResponseComFilmesESeries() {
        // Dado
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Lucas");
        usuario.setUsername("lucas123");
        usuario.setEmail("lucas@gmail.com");

        TmdbFilme filme = new TmdbFilme();
        filme.setId(100L);
        filme.setTitle("Inception");

        TmdbSerie serie = new TmdbSerie();
        serie.setId(200L);
        serie.setName("Breaking Bad");

        List<TmdbFilme> filmesFavoritos = List.of(filme);
        List<TmdbSerie> seriesFavoritas = List.of(serie);

        // Quando
        UsuarioResponse response = usuarioMapper.toDTO(usuario, filmesFavoritos, seriesFavoritas);

        // Então
        assertEquals(1L, response.getId());
        assertEquals("Lucas", response.getNome());
        assertEquals("lucas123", response.getUsername());
        assertEquals("lucas@gmail.com", response.getEmail());
        assertEquals(1, response.getFilmesFavoritos().size());
        assertEquals("Inception", response.getFilmesFavoritos().get(0).getTitle());
        assertEquals(1, response.getSeriesFavoritas().size());
        assertEquals("Breaking Bad", response.getSeriesFavoritas().get(0).getName());
    }

    @Test
    public void deveConverterUsuarioParaUsuarioResponseSemFilmesESeries() {
        // Dado
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Lucas");
        usuario.setUsername("lucas123");
        usuario.setEmail("lucas@gmail.com");

        // Quando
        UsuarioResponse response = usuarioMapper.toDTO(usuario);

        // Então
        assertEquals(1L, response.getId());
        assertEquals("Lucas", response.getNome());
        assertEquals("lucas123", response.getUsername());
        assertEquals("lucas@gmail.com", response.getEmail());
        assertTrue(response.getFilmesFavoritos().isEmpty(), "A lista de filmes favoritos deve estar vazia");
        assertTrue(response.getSeriesFavoritas().isEmpty(), "A lista de séries favoritas deve estar vazia");
    }
}
