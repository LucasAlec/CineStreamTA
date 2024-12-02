package com.tech.ada.spring_cinestream.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ErrorResponseTest {
    @Test
    void dadoObjetoCriadoComConstrutorPadrao_quandoObterTimestamp_entaoDeveRetornarTimestampAtual() {
        // Dado: Um objeto criado com o construtor padrão
        ErrorResponse response = new ErrorResponse();

        // Quando: Obtemos o timestamp
        LocalDateTime timestamp = response.getTimestamp();

        // Então: O timestamp deve estar próximo do momento atual
        assertThat(timestamp).isNotNull();
        assertThat(timestamp).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void dadoObjetoCriadoComConstrutorPadrao_quandoDefinirMensagem_entaoGetterDeveRetornarMensagemDefinida() {
        // Dado: Um objeto criado com o construtor padrão
        ErrorResponse response = new ErrorResponse();

        // Quando: Definimos uma mensagem
        response.setMessage("Erro de validação");

        // Então: O getter deve retornar a mensagem definida
        assertThat(response.getMessage()).isEqualTo("Erro de validação");
    }

}
