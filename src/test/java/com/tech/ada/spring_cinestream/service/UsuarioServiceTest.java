package com.tech.ada.spring_cinestream.service;

import com.tech.ada.spring_cinestream.client.tmdbapi.ApiClient;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbSerie;
import com.tech.ada.spring_cinestream.dto.mapping.UsuarioMapper;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.exception.AlreadyExistsException;
import com.tech.ada.spring_cinestream.exception.AlreadyFavouriteException;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ApiClient tmdbClient;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void testCriarUsuario_ComEmailExistente() throws AlreadyExistsException {
        // Dado
        String email = "allana@gmail.com";
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setEmail(email);

        // Quando
        Mockito.when(usuarioRepository.existsUsuarioByEmail(email)).thenReturn(true);

        // Então
        assertThrows(AlreadyExistsException.class, () -> {
            usuarioService.criar(usuarioRequest);
        });
    }

    @Test
    public void testBuscarPorEmail_ComEmailNaoEncontrado() {
        //Dado
        String email = "usuario@gmail.com";

        // Quando
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Então
        assertThrows(NotFoundException.class, () -> {
            usuarioService.buscarPorEmail(email);
        });
    }

    @Test
    public void testBuscarPorEmail_ComEmailValido() throws NotFoundException {
        // Dado
        String email = "allana@gmail.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        // Quando
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Então
        Usuario result = usuarioService.buscarPorEmail(email);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(email, result.getEmail());
    }

    @Test
    public void testValidateUserLogin_ComEmailNaoRegistrado() {
        // Dado
        String email = "usuario_inexistente@gmail.com";
        String senha = "senha123";

        // Quando
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Então
        Optional<Usuario> result = usuarioService.validateUserLogin(email, senha);
        assert(result.isEmpty());
    }

    @Test
    public void testBuscarPorId_ComIdNaoEncontrado() {
        //Dado
        Long id = 1L;

        // Quando
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Então
        assertThrows(NotFoundException.class, () -> {
            usuarioService.buscarPorId(id);
        });
    }

    @Test
    public void testValidateUserLogin_ComCredenciaisCorretas() {
        //Dado
        String email = "usuario@gmail.com";
        String senha = "senha123";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        // Quando
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        Mockito.when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(true);

        // Então
        Optional<Usuario> result = usuarioService.validateUserLogin(email, senha);
        assert(result.isPresent());
        assert(result.get().getEmail().equals(email));
    }

    @Test
    public void testValidateUserLogin_ComCredenciaisIncorretas() {
        //Dado
        String email = "usuario@gmail.com";
        String senha = "senhaErrada";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        // Quando
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        Mockito.when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(false);

        // Então
        Optional<Usuario> result = usuarioService.validateUserLogin(email, senha);
        assert(result.isEmpty());
    }

    @Test
    public void testFavoritarFilme_ComFilmeJaFavorito() {
        // Dado
        Long idFilme = 123L;
        Usuario usuario = spy(new Usuario());
        TmdbFilme tmdbFilme = new TmdbFilme();

        List<FilmeFavorito> filmesFavoritos = new ArrayList<>();
        filmesFavoritos.add(new FilmeFavorito(tmdbFilme, usuario));
        usuario.setFilmesFavoritos(filmesFavoritos);

        when(usuario.filmeJaEFavorito(idFilme)).thenReturn(true);

        when(tmdbClient.buscarDetalhesFilme(idFilme)).thenReturn(tmdbFilme);

        // Quando
        assertThrows(AlreadyFavouriteException.class, () -> {
            usuarioService.favoritarFilme(idFilme, usuario);
        });
    }

    @Test
    public void testFavoritarFilme_ComFilmeNaoFavorito() throws AlreadyFavouriteException {
        // Dado
        Long idFilme = 123L;
        Usuario usuario = mock(Usuario.class);
        TmdbFilme tmdbFilme = new TmdbFilme();

        when(usuario.filmeJaEFavorito(idFilme)).thenReturn(false);
        when(tmdbClient.buscarDetalhesFilme(idFilme)).thenReturn(tmdbFilme);

        // Quando
        usuarioService.favoritarFilme(idFilme, usuario);

        // Então
        verify(usuario, times(1)).addFilmeFavorito(tmdbFilme);
        verify(usuarioRepository, times(1)).save(usuario);
    }


    @Test
    public void testRemoverFilmeFavorito_ComFilmeNaoFavorito()  {
        // Dado
        Long idFilme = 123L;
        Usuario usuario = mock(Usuario.class);
        TmdbFilme tmdbFilme = new TmdbFilme();
        tmdbFilme.setId(idFilme);

        when(usuario.filmeJaEFavorito(idFilme)).thenReturn(false);

        // Quando e Então
        assertThrows(NotFoundException.class, () -> {
            usuarioService.removerFilmeFavorito(idFilme, usuario);
        });
        verify(usuario, times(0)).removeFilmeFavorito(idFilme);
    }


    @Test
    public void testRemoverFilmeFavorito_ComFilmeFavorito() throws NotFoundException {
        // Dado
        Long idFilme = 123L;
        Usuario usuario = mock(Usuario.class);
        TmdbFilme tmdbFilme = new TmdbFilme();
        tmdbFilme.setId(idFilme);

        when(usuario.filmeJaEFavorito(idFilme)).thenReturn(true);

        doNothing().when(usuario).removeFilmeFavorito(idFilme);

        // Quando
        usuarioService.removerFilmeFavorito(idFilme, usuario);

        // Então
        verify(usuario, times(1)).removeFilmeFavorito(idFilme);
        verify(usuarioRepository, times(1)).save(usuario);
    }


    @Test
    public void testFavoritarSerie_ComSerieJaFavorita() throws AlreadyFavouriteException {
        // Dado
        Long idSerie = 123L;
        Usuario usuario = mock(Usuario.class);
        TmdbSerie tmdbSerie = new TmdbSerie();
        tmdbSerie.setId(idSerie);

        when(usuario.serieJaEFavorita(idSerie)).thenReturn(true);

        // Quando
        assertThrows(AlreadyFavouriteException.class, () -> {
            usuarioService.favoritarSerie(idSerie, usuario);
        });

        // Então
        verify(usuario, times(0)).addSerieFavorita(tmdbSerie);
        verify(usuarioRepository, times(0)).save(usuario);
    }

    @Test
    public void testRemoverSerieFavorita_ComSerieNaoFavorita() throws NotFoundException {
        // Dado
        Long idSerie = 123L;
        Usuario usuario = mock(Usuario.class);

        when(usuario.serieJaEFavorita(idSerie)).thenReturn(false);

        // Quando e Então
        assertThrows(NotFoundException.class, () -> {
            usuarioService.removerSerieFavorita(idSerie, usuario);
        });

        verify(usuario, times(0)).removeSerieFavorita(idSerie);
        verify(usuarioRepository, times(0)).save(usuario);
    }


    @Test
    public void testRemoverSerieFavorito_ComFilmeFavorito() throws NotFoundException {
        // Dado
        Long idSerie = 123L;
        Usuario usuario = mock(Usuario.class);
        TmdbSerie tmdbSerie = new TmdbSerie();
        tmdbSerie.setId(idSerie);

        when(usuario.serieJaEFavorita(idSerie)).thenReturn(true);

        doNothing().when(usuario).removeSerieFavorita(idSerie);

        // Quando
        usuarioService.removerSerieFavorita(idSerie, usuario);

        // Então
        verify(usuario, times(1)).removeSerieFavorita(idSerie);
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
