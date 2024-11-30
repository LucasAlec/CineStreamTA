package com.tech.ada.spring_cinestream.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SerieFavoritaRequestTest {

    @Test
    void dadoObjetoComConstrutorPadrao_quandoAtribuirValoresViaSetters_entaoGettersDevemRetornarValoresCorretos() {
        // Dado: Um objeto criado com o construtor padrão
        SerieFavoritaRequest request = new SerieFavoritaRequest();

        // Quando: Atribuímos valores usando os setters
        request.setIdUsuario(1L);
        request.setIdSerie(2L);

        // Então: Os valores atribuídos devem ser retornados pelos getters
        assertThat(request.getIdUsuario()).isEqualTo(1L);
        assertThat(request.getIdSerie()).isEqualTo(2L);
    }

    @Test
    void dadoObjetoComConstrutorComArgumentos_quandoAcessarGetters_entaoDeveRetornarValoresFornecidos() {
        // Dado: Um objeto criado com o construtor com argumentos
        SerieFavoritaRequest request = new SerieFavoritaRequest(1L, 2L);

        // Quando: Acessamos os valores via getters
        Long idUsuario = request.getIdUsuario();
        Long idSerie = request.getIdSerie();

        // Então: Os valores retornados devem ser os mesmos fornecidos no construtor
        assertThat(idUsuario).isEqualTo(1L);
        assertThat(idSerie).isEqualTo(2L);
    }

    @Test
    void dadoObjetoComConstrutorComArgumentos_quandoAtualizarValoresViaSetters_entaoGettersDevemRetornarNovosValores() {
        // Dado: Um objeto criado com o construtor com argumentos
        SerieFavoritaRequest request = new SerieFavoritaRequest(1L, 2L);

        // Quando: Atualizamos os valores usando os setters
        request.setIdUsuario(3L);
        request.setIdSerie(4L);

        // Então: Os valores atualizados devem ser retornados pelos getters
        assertThat(request.getIdUsuario()).isEqualTo(3L);
        assertThat(request.getIdSerie()).isEqualTo(4L);
    }

}
