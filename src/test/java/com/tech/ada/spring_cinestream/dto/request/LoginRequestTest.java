package com.tech.ada.spring_cinestream.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginRequestTest {

    @Test
    void dadoEmailESenhaValidos_quandoCriarLoginRequest_entaoValoresDevemSerRetornadosCorretamente() {
        // Dado: Um email e uma senha válidos
        String email = "usuario@exemplo.com";
        String senha = "12345";

        // Quando: Criamos um objeto LoginRequest
        LoginRequest request = new LoginRequest(email, senha);

        // Então: Os valores devem ser retornados corretamente
        assertThat(request.email()).isEqualTo(email);
        assertThat(request.senha()).isEqualTo(senha);
    }

    // Dado: Dois objetos LoginRequest com o mesmo email e senha
    // Quando: Comparamos os objetos
    // Então: Eles devem ser considerados iguais
    @Test
    void dadoDoisObjetosLoginRequestComMesmoEmailESenha_quandoComparar_entaoDevemSerIguais() {

        String email = "usuario@exemplo.com";
        String senha = "12345";
        LoginRequest request1 = new LoginRequest(email, senha);
        LoginRequest request2 = new LoginRequest(email, senha);

        boolean iguais = request1.equals(request2);

        assertThat(iguais).isTrue();
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    // Dado: Dois objetos LoginRequest com valores diferentes
    // Quando: Comparamos os objetos
    // Então: Eles não devem ser considerados iguais
    @Test
    void dadoDoisObjetosLoginRequestComValoresDiferentes_quandoComparar_entaoDevemSerDiferentes() {

        LoginRequest request1 = new LoginRequest("usuario1@exemplo.com", "12345");
        LoginRequest request2 = new LoginRequest("usuario2@exemplo.com", "67890");

        boolean iguais = request1.equals(request2);

        assertThat(iguais).isFalse();
    }

    @Test
    void dadoLoginRequest_quandoChamarToString_entaoDeveRetornarRepresentacaoCorreta() {
        // Dado: Um objeto LoginRequest
        LoginRequest request = new LoginRequest("usuario@exemplo.com", "12345");

        // Quando: Chamamos o método toString
        String representacao = request.toString();

        // Então: Deve retornar uma representação legível
        assertThat(representacao).isEqualTo("LoginRequest[email=usuario@exemplo.com, senha=12345]");
    }
}
