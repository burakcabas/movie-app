package com.mcp.movieapp.controller;

import com.mcp.movieapp.entity.Movie;
import com.mcp.movieapp.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Bu sinifin web isteklerini karsilayacagini belirtiyoruz.
@RequestMapping("/api/movies") // Bu sinifin ana adresi.
public class MovieController {

    private final MovieService movieService;

    // Service katmanini buraya bagliyoruz (Dependency Injection)
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // 1. Tum filmleri getiren istek
    // Adres: http://localhost:8080/api/movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    // 2. Arama yapma istegi
    // Adres: http://localhost:8080/api/movies/search?query=matrix
    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String query) {
        // Kullanicinin gonderdigi 'query' kelimesini Service katmanina iletiyoruz.
        return movieService.searchMoviesByTitle(query);
    }
}