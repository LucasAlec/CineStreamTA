package com.tech.ada.spring_cinestream.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FilmeFavoritoRequestTest {



    @Test
    void ObjetoComConstrutorPadrao_quandoAtribuirValoresViaSetters_entaoGettersDevemRetornarValoresCorretos() {
        // Dado: Um objeto criado com o construtor padrão
        FilmeFavoritoRequest request = new FilmeFavoritoRequest();

        // Quando: Atribuímos valores usando os setters
        request.setIdUsuario(1L);
        request.setIdFilme(2L);

        // Então: Os valores atribuídos devem ser retornados pelos getters
        assertThat(request.getIdUsuario()).isEqualTo(1L);
        assertThat(request.getIdFilme()).isEqualTo(2L);
    }

    @Test
    void dadoObjetoComConstrutorComArgumentos_quandoAcessarGetters_entaoDeveRetornarValoresFornecidos() {
        // Dado: Um objeto criado com o construtor com argumentos
        FilmeFavoritoRequest request = new FilmeFavoritoRequest(1L, 2L);

        // Quando: Acessamos os valores usando os getters
        Long idUsuario = request.getIdUsuario();
        Long idFilme = request.getIdFilme();

        // Então: Os valores retornados devem ser os fornecidos no construtor
        assertThat(idUsuario).isEqualTo(1L);
        assertThat(idFilme).isEqualTo(2L);
    }

    @Test
    void dadoObjetoComConstrutorComArgumentos_quandoAtualizarValoresViaSetters_entaoGettersDevemRetornarValoresAtualizados() {
        // Dado: Um objeto criado com o construtor com argumentos
        FilmeFavoritoRequest request = new FilmeFavoritoRequest(1L, 2L);

        // Quando: Atualizamos os valores usando os setters
        request.setIdUsuario(3L);
        request.setIdFilme(4L);

        // Então: Os valores atualizados devem ser retornados pelos getters
        assertThat(request.getIdUsuario()).isEqualTo(3L);
        assertThat(request.getIdFilme()).isEqualTo(4L);
    }



    @Test
    void dadoDoisRequestsDiferentes_quandoComparar_entaoDeveRetornarFalse() {

        // Dado: Dois objetos FilmeFavoritoRequest com IDs diferentes
        FilmeFavoritoRequest request1 = new FilmeFavoritoRequest(1L, 2L);
        FilmeFavoritoRequest request2 = new FilmeFavoritoRequest(3L, 4L);

        // Quando: Comparar os dois objetos usando equals()
        boolean saoIguais = request1.equals(request2);

        // Então: O resultado deve ser false, pois os objetos têm valores diferentes
        assertThat(saoIguais).isFalse();
    }

}
