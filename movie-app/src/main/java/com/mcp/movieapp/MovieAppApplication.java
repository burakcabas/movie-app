package com.mcp.movieapp;

import com.mcp.movieapp.entity.Movie;
import com.mcp.movieapp.repository.MovieRepository;
import com.mcp.movieapp.service.TmdbService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MovieAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }

    // Uygulama ayaga kalktiginda calisacak metot
    @Bean
    public CommandLineRunner initData(MovieRepository movieRepository, TmdbService tmdbService) {
        return args -> {

            // TMDb'den verileri cekip konsola yazdirma testimiz
            tmdbService.fetchPopularMovies();

            // Eski kodlarimiz durabilir, eger veritabani bossa o 3 filmi yine ekler
            if (movieRepository.count() == 0) {
                movieRepository.save(new Movie("The Matrix", "Lana Wachowski", 1999, "Sci-Fi", 8.7));
                movieRepository.save(new Movie("The Godfather", "Francis Ford Coppola", 1972, "Crime", 9.2));
                movieRepository.save(new Movie("Inception", "Christopher Nolan", 2010, "Action", 8.8));
                System.out.println("Baslangic filmleri eklendi!");
            }
        };
    }
}