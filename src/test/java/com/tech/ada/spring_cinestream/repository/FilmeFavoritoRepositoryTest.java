package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmeFavoritoRepositoryTest {

    @Autowired
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private FilmeFavorito filmeFavorito;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        TmdbFilme tmdbFilme = new TmdbFilme();
        tmdbFilme.setId(12345L);

        filmeFavorito = new FilmeFavorito();
        filmeFavorito.setTmdbFilme(tmdbFilme);
        filmeFavorito.setUsuario(usuario);

        filmeFavoritoRepository.save(filmeFavorito);
    }

    @AfterEach
    public void destroy() {
        filmeFavoritoRepository.delete(filmeFavorito);
    }

    @Test
    @Order(1)
    public void filmeFavorito_alreadyPersist_shouldHaveId() {
        Assertions.assertNotNull(filmeFavorito.getId());
    }

    @Test
    @Order(2)
    public void filmeFavorito_searchById_foundFilmeFavorito() {
        var found = filmeFavoritoRepository.findById(filmeFavorito.getId())
                .orElseThrow();
        Assertions.assertNotNull(found);
        Assertions.assertEquals(filmeFavorito.getTmdbId(), found.getTmdbId());
    }

    @Test
    @Order(3)
    public void filmeFavorito_searchByUsuario_foundFilmes() {
        List<FilmeFavorito> filmesFavoritos = filmeFavoritoRepository.findByUsuarioId(usuario.getId());
        Assertions.assertFalse(filmesFavoritos.isEmpty());
    }

    @Test
    @Order(1000)
    public void delete_filmeFavorito_shouldBeDeleted() {
        filmeFavoritoRepository.delete(filmeFavorito);
        Optional<FilmeFavorito> found = filmeFavoritoRepository.findById(filmeFavorito.getId());
        Assertions.assertTrue(found.isEmpty());
    }
}
