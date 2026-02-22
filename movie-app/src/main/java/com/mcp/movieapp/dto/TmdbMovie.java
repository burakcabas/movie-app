package com.mcp.movieapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TmdbMovie {
    // JSON'daki "title" kismini al
    private String title;

    // JSON'daki "release_date" kismini alip bu degiskene koy
    @JsonProperty("release_date")
    private String releaseDate;

    // JSON'daki "vote_average" kismini al
    @JsonProperty("vote_average")
    private Double voteAverage;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public Double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }
}