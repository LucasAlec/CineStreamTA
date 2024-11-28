package com.tech.ada.spring_cinestream.client.tmdbapi;

import com.tech.ada.spring_cinestream.client.tmdbapi.dto.response.*;
import com.tech.ada.spring_cinestream.exception.ApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class ApiClient {
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final String apiBaseUrl;
    private final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    public ApiClient(RestTemplate restTemplate, @Value("${api.key}") String apiKey, @Value("${api.base.url}") String apiBaseUrl) {
        this.apiKey = apiKey;
        this.apiBaseUrl = apiBaseUrl;
        this.restTemplate = restTemplate;
    }

    public Page<TmdbFilme> buscarFilmesPorTitulo(String titulo, Integer page) {
        return makeApiCall(
                "/search/movie",
                getParams(Map.of(
                    "page", page,
                    "query", titulo
                )),
                new ParameterizedTypeReference<>() {}
                );
    }

    public TmdbFilme buscarDetalhesFilme(long id) {
        String path = String.format("/movie/%d", id);

        return makeApiCall(
                path,
                Map.of(),
                TmdbFilme.class
        );
    }


    public Page<TmdbFilme> buscarFilmesPorAnoLancamento(String ano, Integer page) {
        return makeApiCall(
                "/search/movie",
                getParams(Map.of(
                    "query", " ",
                    "page", page,
                    "year", ano
                )),
                new ParameterizedTypeReference<>() {}
        );
    }

    public Page<TmdbSerie> buscarSeriesPorTitulo(String titulo, Integer page) {
        return makeApiCall(
                "/search/tv",
                getParams(Map.of(
                    "api_key", apiKey,
                    "query", titulo,
                    "page", page
                )),
                new ParameterizedTypeReference<>() {}
                );
    }

    public Page<TmdbSerie> buscarSeriesPorAnoLancamento(String ano, Integer page) {
        return makeApiCall(
                "/search/tv",
                getParams(Map.of(
                    "page", page,
                    "query", " ",
                    "first_air_date_year", ano
                )),
                new ParameterizedTypeReference<>() {}
        );
    }

    public TmdbSerie buscarDetalhesSerie(long id) {
        String path = String.format("/tv/%d", id);
        return makeApiCall(
                path,
                Map.of(),
                TmdbSerie.class
        );
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        return headers;
    }

    private <T> T makeApiCall(String path, Map<String, Object> queryParams, ParameterizedTypeReference<T> responseType) {
        String url = buildUrl(path, queryParams);
        logger.info("Fazendo chamada à API: {}", url.replace(apiKey, "API_KEY"));
        try {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeader()), responseType).getBody();
        } catch (Exception e) {
            logger.error("Erro na chamada à API: {}", url.replace(apiKey, "API_KEY"), e);
            throw new ApiClientException("Erro na chamada API", e);
        }
    }

    private <T> T makeApiCall(String path, Map<String, Object> queryParams, Class<T> responseType) {
        String url = buildUrl(path, queryParams);
        logger.info("Fazendo chamada à API: {}", url.replace(apiKey, "API_KEY"));
        try {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeader()), responseType).getBody();
        } catch (Exception e) {
            logger.error("Erro na chamada à API: {}", url.replace(apiKey, "API_KEY"), e);
            throw new ApiClientException("Erro na chamada API", e);
        }
    }

    private String buildUrl(String path, Map<String, Object> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBaseUrl).path(path);
        queryParams.forEach(builder::queryParam);
        return builder.toUriString();
    }

    private Map<String, Object> getParams(Map<String, Object> params) {
        Map<String, Object> defaults = getParams();
        params.putAll(defaults);
        return params;
    }

    private Map<String, Object> getParams() {
        return Map.of("language", "pt-BR");
    }
}
