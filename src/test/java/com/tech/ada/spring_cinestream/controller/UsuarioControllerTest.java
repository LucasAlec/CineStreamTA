package com.tech.ada.spring_cinestream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.ada.spring_cinestream.exception.AlreadyFavouriteException;
import com.tech.ada.spring_cinestream.exception.ApiClientException;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void dadoEmailValido_quandoBuscarUsuarioPorEmail_entaoRetornaUsuario() throws Exception {
        // Dado
        String email = "alex@gmail.com";
        Usuario usuario = new Usuario("Alex", "alexmendes", email, "senha123");

        // Quando
        when(usuarioService.buscarPorEmail(email)).thenReturn(usuario);

        // Então
        mockMvc.perform(get("/usuario/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nome").value("Alex"));
    }


    @Test
    public void dadoEmailNaoExistente_quandoBuscarUsuarioPorEmail_entaoRetornaNotFound() throws Exception {
        // Dado
        String email = "naoexistente@gmail.com";

        // Quando
        when(usuarioService.buscarPorEmail(email)).thenThrow(new NotFoundException("Usuário não encontrado"));

        // Então
        mockMvc.perform(get("/usuario/{email}", email))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Usuário não encontrado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    public void dadoFilmeNaoFavoritado_quandoAdicionarFilmeFavorito_entaoRetornaSucesso() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                usuario,
                "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doNothing().when(usuarioService).favoritarFilme(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/filme/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Filme adicionado como favorito."));
    }


    @Test
    public void dadoFilmeFavoritado_quandoAdicionarFilmeFavoritoEntaoNaoAdicionaNovamente() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                usuario,
                "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doThrow(new AlreadyFavouriteException("Esse filme já está na lista de favorito do usuário."))
                .when(usuarioService).favoritarFilme(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/filme/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isAlreadyReported())
                .andExpect(content().string("Esse filme já está na lista de favorito do usuário."));
    }


    @Test
    public void dadoFilmeFavoritado_quandoRemoverFilmeFavorito_entaoRemoveComSucesso() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doNothing().when(usuarioService).removerFilmeFavorito(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/filme/remover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Filme removido da lista de favoritos."));
    }

    @Test
    public void dadoFilmeNaoFavoritado_quandoRemoverFilmeFavorito_entaoRetornaErro() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doThrow(new NotFoundException("O filme não pertencia a lista de favoritos."))
                .when(usuarioService).removerFilmeFavorito(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/filme/remover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("O filme não pertencia a lista de favoritos."));
    }


    @Test
    public void dadoSerieNaoFavoritada_quandoAdicionarSerieFavorita_entaoRetornaSucesso() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doNothing().when(usuarioService).favoritarSerie(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/serie/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Série adicionada como favorita."));
    }

    @Test
    public void dadoSerieFavoritada_quandoAdicionarSerieFavorita_entaoRetornaErro() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doThrow(new AlreadyFavouriteException("Esse filme já está na lista de favorito do usuário."))
                .when(usuarioService).favoritarSerie(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/serie/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isAlreadyReported())
                .andExpect(content().string("Esse filme já está na lista de favorito do usuário."));
    }


    @Test
    public void dadoSerieFavoritada_quandoRemoverSerieFavorita_entaoRetornaSucesso() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doNothing().when(usuarioService).removerSerieFavorita(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/serie/remover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Série removida da lista de favoritos."));
    }

    @Test
    public void dadoSerieNaoFavoritada_quandoRemoverSerieFavorita_entaoRetornaErro() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doThrow(new NotFoundException("A Série não pertencia a lista de favoritos."))
                .when(usuarioService).removerSerieFavorita(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/serie/remover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("A Série não pertencia a lista de favoritos."));
    }

    @Test
    public void dadoErroApiClient_quandoAdicionarFilmeFavorito_entaoRetornaErro() throws Exception {
        // Dado
        Long idTmdb = 123L;
        Usuario usuario = new Usuario("Alex", "alexmendes", "alex@gmail.com", "senha123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                usuario,
                "senha123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Quando
        doThrow(new ApiClientException("Falha na comunicação com o serviço externo"))
                .when(usuarioService).favoritarFilme(idTmdb, usuario);

        // Então
        mockMvc.perform(post("/usuario/favorito/filme/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("Falha ao adicionar filme: Falha na comunicação com o serviço externo"));
    }

    @Test
    public void dadoUsuarioNaoAutenticado_quandoAdicionarFilmeFavorito_entaoRetornaUnauthorized() throws Exception {
        // Dado
        Long idTmdb = 123L;

        SecurityContextHolder.clearContext();

        // Quando
        mockMvc.perform(post("/usuario/favorito/filme/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(idTmdb)))
                .andExpect(status().isForbidden());
    }
}
