package com.mcp.movieapp.service;

import com.mcp.movieapp.entity.Movie;
import com.mcp.movieapp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring Boot'a "Bu sinif projenin beynidir (is mantigi)" diyoruz.
public class MovieService {

    // Dependency Injection (Bagimlilik Enjeksiyonu):
    // Repository'yi burada kullanmak icin cagiriyoruz.
    private final MovieRepository movieRepository;

    // Constructor (Yapici Metot)
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 1. Veritabanindaki tum filmleri getiren metot
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
        // findAll() metodu arka planda "SELECT * FROM movies" sorgusunu calistirir.
    }

    // 2. Ileride LLM (Yapay Zeka) tarafindan cagirilacak ozel bir arama metodu:
    // Film adina gore arama yapmak icin kullanacagiz.
    public List<Movie> searchMoviesByTitle(String keyword) {
        // Not: Bu metot su an hata verebilir cunku Repository'de henuz tanimlamadik.
        // Bir sonraki adimda bunu nasil cozecegimizi gorecegiz!
        return movieRepository.findByTitleContainingIgnoreCase(keyword);
    }
}