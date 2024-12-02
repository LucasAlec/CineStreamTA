package com.tech.ada.spring_cinestream.dto.token;

import com.tech.ada.spring_cinestream.model.Token;
import com.tech.ada.spring_cinestream.model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TokenResultTest {
    @Test
    void dadoTokenEStatusDeExpiracao_quandoCriarTokenResult_entaoValoresDevemSerRetornadosCorretamente() {
        // Dado: Um token e um status de expiração
        Usuario usuario = new Usuario("Lucas", "lucas_user", "lucas@gmail.com","apt1357");
        Token token = new Token("abcdef123456", Instant.now(), usuario);
        boolean isExpired = false;

        // Quando: Criamos um objeto TokenResult
        TokenResult result = new TokenResult(token, isExpired);

        // Então: Os valores devem ser retornados corretamente
        assertThat(result.token()).isEqualTo(token);
        assertThat(result.isExpired()).isEqualTo(isExpired);
    }

    @Test
    void dadoDoisObjetosTokenResultComMesmoTokenEStatus_quandoComparar_entaoDevemSerIguais() {
        // Dado: Dois objetos TokenResult com os mesmos valores
        Usuario usuario = new Usuario("Lucas", "lucas_user", "lucas@gmail.com", "apt1357");
        Token token = new Token("abcdef123456", Instant.now(), usuario);

        TokenResult result1 = new TokenResult(token, true);
        TokenResult result2 = new TokenResult(token, true);

        // Quando: Comparamos os dois objetos
        boolean iguais = result1.equals(result2);

        // Então: Eles devem ser considerados iguais
        assertThat(iguais).isTrue();
        assertThat(result1.hashCode()).isEqualTo(result2.hashCode());
    }

    @Test
    void dadoDoisObjetosTokenResultComValoresDiferentes_quandoComparar_entaoDevemSerDiferentes() {
        // Dado: Dois objetos TokenResult com valores diferentes
        Usuario usuario1 = new Usuario("Lucas", "lucas_user", "lucas@gmail.com","apt1357");
        Usuario usuario2 = new Usuario("Ana", "ana_user", "ana@gmail.com", "ana1234");

        Token token1 = new Token("abcdef123456", Instant.now(), usuario1);
        Token token2 = new Token("123456abcdef", Instant.now(), usuario2);

        TokenResult result1 = new TokenResult(token1, false);
        TokenResult result2 = new TokenResult(token2, true);

        // Quando: Comparamos os dois objetos
        boolean iguais = result1.equals(result2);

        // Então: Eles não devem ser considerados iguais
        assertThat(iguais).isFalse();
    }

}
