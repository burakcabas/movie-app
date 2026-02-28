package com.mcp.movieapp.dto;

import java.util.List;

public class TmdbGenreResponse {
    private List<TmdbGenre> genres;

    public List<TmdbGenre> getGenres() { return genres; }
    public void setGenres(List<TmdbGenre> genres) { this.genres = genres; }
}