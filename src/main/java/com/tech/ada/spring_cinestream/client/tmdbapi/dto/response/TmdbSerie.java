package com.tech.ada.spring_cinestream.client.tmdbapi.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

import java.util.List;
import java.util.Objects;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Embeddable
public class TmdbSerie {
    @ElementCollection
    private List<Integer> genreIds;
    private Long id;
    private String name;
    @Column(length = 6496)
    private String overview;
    private String firstAirDate;
    private double voteAverage;
    private Integer voteCount;
    private String posterPath;

    public TmdbSerie() {}

    public TmdbSerie(List<Integer> genreIds, Long id, String name, String overview, String firstAirDate, double voteAverage, Integer voteCount, String posterPath) {
        this.genreIds = genreIds;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TmdbSerie tmdbSerie)) return false;
        return Objects.equals(id, tmdbSerie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}