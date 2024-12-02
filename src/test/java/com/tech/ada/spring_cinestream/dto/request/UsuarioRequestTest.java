package com.tech.ada.spring_cinestream.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsuarioRequestTest {

    @Test
    void dadoObjetoComConstrutorPadrao_quandoAtribuirValoresViaSetters_entaoGettersDevemRetornarValoresCorretos() {
        // Dado: Um objeto criado com o construtor padrão
        UsuarioRequest request = new UsuarioRequest();

        // Quando: Atribuímos valores aos atributos usando os setters
        request.setNome("Lucas");
        request.setUsername("lucas_alec");
        request.setEmail("lucas@gmail.com");
        request.setSenha("123456");

        // Então: Os getters devem retornar os valores atribuídos
        assertThat(request.getNome()).isEqualTo("Lucas");
        assertThat(request.getUsername()).isEqualTo("lucas_alec");
        assertThat(request.getEmail()).isEqualTo("lucas@gmail.com");
        assertThat(request.getSenha()).isEqualTo("123456");
    }

    @Test
    void dadoObjetoComValoresAtualizadosViaSetters_quandoAcessarGetters_entaoDevemRetornarNovosValores() {
        // Dado: Um objeto com valores atribuídos
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Lucas");
        request.setUsername("lucas_alec");
        request.setEmail("lucas@gmail.com");
        request.setSenha("123456");

        // Quando: Atualizamos os valores usando os setters
        request.setNome("Ana");
        request.setUsername("ana_ale");
        request.setEmail("ana@gmail.com");
        request.setSenha("abcdef");

        // Então: Os getters devem retornar os valores atualizados
        assertThat(request.getNome()).isEqualTo("Ana");
        assertThat(request.getUsername()).isEqualTo("ana_ale");
        assertThat(request.getEmail()).isEqualTo("ana@gmail.com");
        assertThat(request.getSenha()).isEqualTo("abcdef");
    }

    @Test
    void dadoObjetoComConstrutorPadrao_quandoNenhumValorForDefinido_entaoGettersDevemRetornarNulo() {
        // Dado: Um objeto criado com o construtor padrão
        UsuarioRequest request = new UsuarioRequest();

        // Quando: Não definimos nenhum valor nos atributos
        // Então: Os getters devem retornar null

        assertThat(request.getNome()).isNull();
        assertThat(request.getUsername()).isNull();
        assertThat(request.getEmail()).isNull();
        assertThat(request.getSenha()).isNull();
    }

}
