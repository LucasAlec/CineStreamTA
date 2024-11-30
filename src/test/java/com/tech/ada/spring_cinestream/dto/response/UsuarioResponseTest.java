package com.tech.ada.spring_cinestream.dto.response;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.TmdbFilme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsuarioResponseTest {
    @Test
    void dadoObjetoCriadoComConstrutorPadrao_quandoAtribuirValoresViaSetters_entaoGettersDevemRetornarValoresCorretos() {
        // Dado: Um objeto criado com o construtor padrão
        TmdbFilme filme = new TmdbFilme();

        // Quando: Atribuímos valores usando os setters
        filme.setTitle("Inception");
        filme.setOverview("A mind-bending thriller");
        filme.setReleaseDate("2010-07-16");
        filme.setVoteAverage(8.8);
        filme.setPosterPath("/poster.jpg");
        filme.setId(1L);

        // Então: Os getters devem retornar os valores atribuídos
        assertThat(filme.getTitle()).isEqualTo("Inception");
        assertThat(filme.getOverview()).isEqualTo("A mind-bending thriller");
        assertThat(filme.getReleaseDate()).isEqualTo("2010-07-16");
        assertThat(filme.getVoteAverage()).isEqualTo(8.8);
        assertThat(filme.getPosterPath()).isEqualTo("/poster.jpg");
        assertThat(filme.getId()).isEqualTo(1L);
    }

    @Test
    void dadoObjetoCriadoComConstrutorComArgumentos_quandoAcessarGetters_entaoValoresDevemSerCorretos() {
        // Dado: Um objeto criado com o construtor com argumentos
        TmdbFilme filme = new TmdbFilme(
                "Interstellar",
                "A journey through space and time",
                "2014-11-07",
                8.6,
                "/interstellar.jpg",
                2L
        );

        // Quando: Acessamos os valores via getters
        String title = filme.getTitle();
        String overview = filme.getOverview();
        String releaseDate = filme.getReleaseDate();
        double voteAverage = filme.getVoteAverage();
        String posterPath = filme.getPosterPath();
        Long id = filme.getId();

        // Então: Os valores retornados devem ser os fornecidos no construtor
        assertThat(title).isEqualTo("Interstellar");
        assertThat(overview).isEqualTo("A journey through space and time");
        assertThat(releaseDate).isEqualTo("2014-11-07");
        assertThat(voteAverage).isEqualTo(8.6);
        assertThat(posterPath).isEqualTo("/interstellar.jpg");
        assertThat(id).isEqualTo(2L);
    }

    @Test
    void dadoDoisObjetosComMesmoId_quandoComparar_entaoDevemSerIguais() {
        // Dado: Dois objetos TmdbFilme com o mesmo ID
        TmdbFilme filme1 = new TmdbFilme("Inception", "Overview1", "2010-07-16", 8.8, "/poster1.jpg", 1L);
        TmdbFilme filme2 = new TmdbFilme("Another Title", "Overview2", "2015-07-16", 9.0, "/poster2.jpg", 1L);

        // Quando: Comparamos os objetos
        boolean iguais = filme1.equals(filme2);

        // Então: Eles devem ser considerados iguais, pois possuem o mesmo ID
        assertThat(iguais).isTrue();
        assertThat(filme1.hashCode()).isEqualTo(filme2.hashCode());
    }

    @Test
    void dadoDoisObjetosComIdsDiferentes_quandoComparar_entaoDevemSerDiferentes() {
        // Dado: Dois objetos TmdbFilme com IDs diferentes
        TmdbFilme filme1 = new TmdbFilme("Inception", "Overview1", "2010-07-16", 8.8, "/poster1.jpg", 1L);
        TmdbFilme filme2 = new TmdbFilme("Interstellar", "Overview2", "2014-11-07", 8.6, "/poster2.jpg", 2L);

        // Quando: Comparamos os objetos
        boolean iguais = filme1.equals(filme2);

        // Então: Eles não devem ser considerados iguais, pois possuem IDs diferentes
        assertThat(iguais).isFalse();
    }
}
