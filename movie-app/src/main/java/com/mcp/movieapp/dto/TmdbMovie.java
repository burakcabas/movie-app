package com.mcp.movieapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List; // Bunu import etmeyi unutma

public class TmdbMovie {
    private String title;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double voteAverage;

    // YENI EKLENEN KISIM: Tur numaralarini alacagimiz liste
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public Double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }

    // YENI EKLENEN GETTER/SETTER
    public List<Integer> getGenreIds() { return genreIds; }
    public void setGenreIds(List<Integer> genreIds) { this.genreIds = genreIds; }
}