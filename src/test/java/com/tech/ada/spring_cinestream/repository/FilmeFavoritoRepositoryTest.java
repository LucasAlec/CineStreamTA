package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import com.tech.ada.spring_cinestream.model.SerieFavorita;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class FilmeFavoritoRepositoryTest {

    @Autowired
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private FilmeFavorito filmeFavorito;

    @BeforeEach
    public void setup() {
        usuario = criarUsuario("joao@email.com", "senha123");
        filmeFavorito = criarFilmeFavorito(usuario, 12345L);
    }

    private Usuario criarUsuario(String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuarioRepository.saveAndFlush(usuario);
    }

    private FilmeFavorito criarFilmeFavorito(Usuario usuario, Long tmdbId) {
        TmdbFilme tmdbFilme = new TmdbFilme();
        tmdbFilme.setId(tmdbId);

        FilmeFavorito filmeFavorito = new FilmeFavorito();
        filmeFavorito.setTmdbFilme(tmdbFilme);
        filmeFavorito.setUsuario(usuario);
        return filmeFavoritoRepository.saveAndFlush(filmeFavorito);
    }


    @AfterEach
    public void destroy() {
        filmeFavoritoRepository.delete(filmeFavorito);
        filmeFavoritoRepository.flush();
    }

    @Test
    @Order(1)
    public void filmeFavorito_alreadyPersist_shouldHaveId() {
        Assertions.assertNotNull(filmeFavorito.getId(), "O ID do filme favorito não pode ser nulo.");
    }

    @Test
    @Order(2)
    public void filmeFavorito_searchById_foundFilmeFavorito() {
        Optional<FilmeFavorito> found = filmeFavoritoRepository.findById(filmeFavorito.getId());
        Assertions.assertTrue(found.isPresent(), "O filme favorito deve ser encontrado.");
        Assertions.assertEquals(filmeFavorito.getTmdbId(), found.get().getTmdbId(), "O ID do TMDB deve ser igual.");
    }


    @Test
    @Order(3)
    public void filmeFavorito_searchByUsuario_foundFilmes() {
        List<FilmeFavorito> filmesFavoritos = filmeFavoritoRepository.findByUsuarioId(usuario.getId());
        Assertions.assertFalse(filmesFavoritos.isEmpty(), "A lista de filmes favoritos não deve estar vazia.");
        Assertions.assertEquals(1, filmesFavoritos.size(), "Deve haver exatamente 1 filme favorito associado ao usuário.");
    }

    @Test
    @Order(4)
    public void filmeFavorito_searchByInvalidId_notFound() {
        Optional<FilmeFavorito> found = filmeFavoritoRepository.findById(-1L);
        Assertions.assertTrue(found.isEmpty(), "Não deve haver resultados para um ID inexistente.");
    }

    @Test
    @Order(1000)
    public void delete_filmeFavorita_shouldBeDeleted() {
        filmeFavoritoRepository.delete(filmeFavorito);
        filmeFavoritoRepository.flush();
        Optional<FilmeFavorito> found = filmeFavoritoRepository.findById(filmeFavorito.getId());
        Assertions.assertTrue(found.isEmpty(), "A Filme favorita não foi excluída corretamente.");
    }

}
