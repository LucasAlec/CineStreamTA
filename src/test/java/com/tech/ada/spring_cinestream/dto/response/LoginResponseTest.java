package com.tech.ada.spring_cinestream.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginResponseTest {

    @Test
    void dadoTokenEEmailValidos_quandoCriarLoginResponse_entaoValoresDevemSerRetornadosCorretamente() {
        // Dado: Um token e um email válidos
        String token = "abcdef123456";
        String email = "lucas@gamail.com";

        // Quando: Criamos um objeto LoginResponse
        LoginResponse response = new LoginResponse(token, email);

        // Então: Os valores devem ser retornados corretamente
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.email()).isEqualTo(email);
    }

    @Test
    void dadoDoisObjetosLoginResponseComMesmosValores_quandoComparar_entaoDevemSerIguais() {
        // Dado: Dois objetos LoginResponse com os mesmos valores
        String token = "abcdef123456";
        String email = "lucas@gamail.com";
        LoginResponse response1 = new LoginResponse(token, email);
        LoginResponse response2 = new LoginResponse(token, email);

        // Quando: Comparamos os objetos
        boolean saoIguais = response1.equals(response2);

        // Então: Eles devem ser considerados iguais
        assertThat(saoIguais).isTrue();
        assertThat(response1.hashCode()).isEqualTo(response2.hashCode());
    }

    @Test
    void dadoDoisObjetosLoginResponseComValoresDiferentes_quandoComparar_entaoDevemSerDiferentes() {
        // Dado: Dois objetos LoginResponse com valores diferentes
        LoginResponse response1 = new LoginResponse("token1", "lucas1@gmail.com");
        LoginResponse response2 = new LoginResponse("token2", "lucas2@gmail.com");

        // Quando: Comparamos os objetos
        boolean saoIguais = response1.equals(response2);

        // Então: Eles não devem ser considerados iguais
        assertThat(saoIguais).isFalse();
    }

}
