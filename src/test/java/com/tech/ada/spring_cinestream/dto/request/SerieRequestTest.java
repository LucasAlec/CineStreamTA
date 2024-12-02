package com.tech.ada.spring_cinestream.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SerieRequestTest {

    @Test
    void dadoObjetoComConstrutorPadrao_quandoAtribuirTituloViaSetter_entaoGetterDeveRetornarTituloCorreto() {
        // Dado: Um objeto criado com o construtor padrão
        SerieRequest request = new SerieRequest();

        // Quando: Atribuímos um título usando o setter
        request.setTitulo("Breaking Bad");

        // Então: O título retornado pelo getter deve ser o mesmo atribuído
        assertThat(request.getTitulo()).isEqualTo("Breaking Bad");
    }

    @Test
    void dadoObjetoComConstrutorComArgumento_quandoAcessarGetter_entaoDeveRetornarTituloFornecido() {
        // Dado: Um objeto criado com o construtor com argumentos
        SerieRequest request = new SerieRequest("Stranger Things");

        // Quando: Acessamos o título via getter
        String titulo = request.getTitulo();

        // Então: O título retornado deve ser o mesmo fornecido no construtor
        assertThat(titulo).isEqualTo("Stranger Things");
    }

    @Test
    void dadoObjetoComConstrutorComArgumento_quandoAtualizarTituloViaSetter_entaoGetterDeveRetornarNovoTitulo() {
        // Dado: Um objeto criado com o construtor com argumentos
        SerieRequest request = new SerieRequest("Old Title");

        // Quando: Atualizamos o título usando o setter
        request.setTitulo("New Title");

        // Então: O título retornado pelo getter deve ser o novo valor atribuído
        assertThat(request.getTitulo()).isEqualTo("New Title");
    }

}
