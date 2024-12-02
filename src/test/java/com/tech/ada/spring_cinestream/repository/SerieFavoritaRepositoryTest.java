package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.SerieFavorita;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
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
public class SerieFavoritaRepositoryTest {

    @Autowired
    private SerieFavoritaRepository serieFavoritaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private SerieFavorita serieFavorita;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        TmdbSerie tmdbSerie = new TmdbSerie();
        tmdbSerie.setId(12345L);

        serieFavorita = new SerieFavorita();
        serieFavorita.setTmdbSerie(tmdbSerie);
        serieFavorita.setUsuario(usuario);

        serieFavoritaRepository.save(serieFavorita);
    }

    @AfterEach
    public void destroy() {
        serieFavoritaRepository.delete(serieFavorita);
        serieFavoritaRepository.flush();
    }

    @Test
    @Order(1)
    public void serieFavorita_alreadyPersist_shouldHaveId() {
        Assertions.assertNotNull(serieFavorita.getId());
    }

    @Test
    @Order(2)
    public void serieFavorita_searchById_foundSerieFavorita() {
        var found = serieFavoritaRepository.findById(serieFavorita.getId())
                .orElseThrow();
        Assertions.assertNotNull(found);
        Assertions.assertEquals(serieFavorita.getTmdbSerie().getId(), found.getTmdbSerie().getId());
    }

    @Test
    @Order(3)
    public void serieFavorita_searchByUsuario_foundSeries() {
        List<SerieFavorita> seriesFavoritas = serieFavoritaRepository.findByUsuarioId(usuario.getId());
        Assertions.assertFalse(seriesFavoritas.isEmpty());
    }

    @Test
    @Order(1000)
    public void delete_serieFavorita_shouldBeDeleted() {
        serieFavoritaRepository.delete(serieFavorita);
        serieFavoritaRepository.flush();
        Optional<SerieFavorita> found = serieFavoritaRepository.findById(serieFavorita.getId());
        Assertions.assertTrue(found.isEmpty(), "A SerieFavorita não foi excluída corretamente.");
    }

}
