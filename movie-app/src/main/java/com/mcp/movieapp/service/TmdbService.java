package com.mcp.movieapp.service;

import com.mcp.movieapp.dto.TmdbMovie;
import com.mcp.movieapp.dto.TmdbResponse;
import com.mcp.movieapp.entity.Movie;
import com.mcp.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Veritabanina kaydetmek icin Repository'i buraya bagliyoruz
    @Autowired
    private MovieRepository movieRepository;

    public void fetchPopularMovies() {
        String url = baseUrl + "/movie/popular?api_key=" + apiKey + "&language=tr-TR&page=1";

        System.out.println("TMDb'den filmler cekiliyor ve veritabanina kaydediliyor...");

        try {
            // Artik veriyi String degil, olusturdugumuz TmdbResponse objesi olarak aliyoruz
            TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);

            if (response != null && response.getResults() != null) {
                // Gelen her bir film icin dongu baslatiyoruz
                for (TmdbMovie tmdbMovie : response.getResults()) {

                    // TMDb verisini kendi Movie objemize donusturuyoruz
                    Movie newMovie = new Movie();
                    newMovie.setTitle(tmdbMovie.getTitle());

                    // TMDb yili "2024-01-15" gibi gonderir, biz sadece ilk 4 hanesini (yili) alacagiz
                    if(tmdbMovie.getReleaseDate() != null && tmdbMovie.getReleaseDate().length() >= 4) {
                        newMovie.setReleaseYear(Integer.parseInt(tmdbMovie.getReleaseDate().substring(0, 4)));
                    }

                    newMovie.setRating(tmdbMovie.getVoteAverage());
                    newMovie.setGenre("Bilinmiyor"); // TMDb'de turler karisiktir, simdilik boyle birakalim
                    newMovie.setDirector("Bilinmiyor"); // TMDb yonetmeni baska bir adresten verir

                    // Filmi veritabanina kaydet
                    movieRepository.save(newMovie);
                    System.out.println("Kaydedildi: " + newMovie.getTitle());
                }
                System.out.println("Islem basariyla tamamlandi!");
            }

        } catch (Exception e) {
            System.out.println("Bir hata olustu: " + e.getMessage());
        }
    }
}