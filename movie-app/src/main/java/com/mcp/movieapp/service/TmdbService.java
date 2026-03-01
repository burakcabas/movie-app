package com.mcp.movieapp.service;

import com.mcp.movieapp.dto.TmdbGenre;
import com.mcp.movieapp.dto.TmdbGenreResponse;
import com.mcp.movieapp.dto.TmdbMovie;
import com.mcp.movieapp.dto.TmdbResponse;
import com.mcp.movieapp.entity.Movie;
import com.mcp.movieapp.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TmdbService {

    // YENİ: Profesyonel Logger Tanımlaması
    private static final Logger logger = LoggerFactory.getLogger(TmdbService.class);

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MovieRepository movieRepository;

    private Map<Integer, String> fetchGenreDictionary() {
        String url = baseUrl + "/genre/movie/list?api_key=" + apiKey + "&language=tr-TR";
        Map<Integer, String> genreMap = new HashMap<>();

        try {
            TmdbGenreResponse response = restTemplate.getForObject(url, TmdbGenreResponse.class);
            if (response != null && response.getGenres() != null) {
                for (TmdbGenre genre : response.getGenres()) {
                    genreMap.put(genre.getId(), genre.getName());
                }
            }
        } catch (Exception e) {
            // Hata durumunda logger.error kullanıyoruz
            logger.error("Tür sözlüğü çekilirken hata oluştu: {}", e.getMessage());
        }
        return genreMap;
    }

    public void fetchPopularMovies() {
        // Bilgi mesajları için logger.info kullanıyoruz
        logger.info("TMDb'den veriler güncelleniyor, sayfalar taranıyor...");

        try {
            Map<Integer, String> genreDictionary = fetchGenreDictionary();
            int eklenenFilmSayisi = 0;

            for (int page = 1; page <= 5; page++) {
                String url = baseUrl + "/movie/popular?api_key=" + apiKey + "&language=tr-TR&page=" + page;
                TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);

                if (response != null && response.getResults() != null) {
                    for (TmdbMovie tmdbMovie : response.getResults()) {

                        if (!movieRepository.existsByTitle(tmdbMovie.getTitle())) {
                            Movie newMovie = new Movie();
                            newMovie.setTitle(tmdbMovie.getTitle());

                            if(tmdbMovie.getReleaseDate() != null && tmdbMovie.getReleaseDate().length() >= 4) {
                                newMovie.setReleaseYear(Integer.parseInt(tmdbMovie.getReleaseDate().substring(0, 4)));
                            }
                            newMovie.setRating(tmdbMovie.getVoteAverage());
                            newMovie.setDirector("Bilinmiyor");

                            if (tmdbMovie.getGenreIds() != null && !tmdbMovie.getGenreIds().isEmpty()) {
                                List<String> genreNames = new ArrayList<>();
                                for (Integer genreId : tmdbMovie.getGenreIds()) {
                                    String name = genreDictionary.get(genreId);
                                    if (name != null) {
                                        genreNames.add(name);
                                    }
                                }
                                newMovie.setGenre(String.join(", ", genreNames));
                            } else {
                                newMovie.setGenre("Bilinmiyor");
                            }

                            movieRepository.save(newMovie);
                            eklenenFilmSayisi++;
                        }
                    }
                }
            }
            // İşlem bittiğinde sonuç raporu
            logger.info("İşlem tamamlandı. Toplam yeni eklenen film sayısı: {}", eklenenFilmSayisi);

        } catch (Exception e) {
            logger.error("TMDb'den film çekilirken genel bir hata oluştu: {}", e.getMessage());
        }
    }
}