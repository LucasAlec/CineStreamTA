package com.tech.ada.spring_cinestream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.ada.spring_cinestream.dto.request.LoginRequest;
import com.tech.ada.spring_cinestream.dto.request.UsuarioRequest;
import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.service.JWTService;
import com.tech.ada.spring_cinestream.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JWTService jwtService;

    @Test
    public void dadoUsuarioValido_quandoFeitoLogin_entaoRetornaToken() throws Exception {
        // Dado
        LoginRequest loginRequest = new LoginRequest("ana@example.com", "senha123");
        Usuario usuario = new Usuario();
        usuario.setEmail("ana@example.com");

        Mockito.when(usuarioService.validateUserLogin("ana@example.com", "senha123"))
                .thenReturn(Optional.of(usuario));
        Mockito.when(jwtService.generateToken(usuario))
                .thenReturn("fake-jwt-token");

        // Quando
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                // Então
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ana@example.com"));
    }

}
