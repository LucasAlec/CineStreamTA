package com.tech.ada.spring_cinestream.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FilmeRequestTest {

    @Test
    void dadoObjetoComConstrutorPadrao_quandoAtribuirTituloViaSetter_entaoGetterDeveRetornarTituloCorreto() {
        // Dado: Um objeto criado com o construtor padrão
        FilmeRequest request = new FilmeRequest();

        // Quando: Atribuímos um título usando o setter
        request.setTitulo("Inception");

        // Então: O título retornado pelo getter deve ser o mesmo atribuído
        assertThat(request.getTitulo()).isEqualTo("Inception");
    }

    @Test
    void dadoObjetoComConstrutorComArgumento_quandoAcessarGetter_entaoDeveRetornarTituloFornecido() {
        // Dado: Um objeto criado com o construtor com argumentos
        FilmeRequest request = new FilmeRequest("Interstellar");

        // Quando: Acessamos o título via getter
        String titulo = request.getTitulo();

        // Então: O título retornado deve ser o mesmo fornecido no construtor
        assertThat(titulo).isEqualTo("Interstellar");
    }

}
