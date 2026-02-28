package com.mcp.movieapp.service;

import com.mcp.movieapp.dto.TmdbGenre;
import com.mcp.movieapp.dto.TmdbGenreResponse;
import com.mcp.movieapp.dto.TmdbMovie;
import com.mcp.movieapp.dto.TmdbResponse;
import com.mcp.movieapp.entity.Movie;
import com.mcp.movieapp.repository.MovieRepository;
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

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MovieRepository movieRepository;

    // 1. YENI METOT: TMDb'den Tur Sozlugunu Ceker ve Java Map'ine donusturur
    private Map<Integer, String> fetchGenreDictionary() {
        String url = baseUrl + "/genre/movie/list?api_key=" + apiKey + "&language=tr-TR";
        Map<Integer, String> genreMap = new HashMap<>();

        try {
            TmdbGenreResponse response = restTemplate.getForObject(url, TmdbGenreResponse.class);
            if (response != null && response.getGenres() != null) {
                for (TmdbGenre genre : response.getGenres()) {
                    genreMap.put(genre.getId(), genre.getName()); // Ornek: (28, "Aksiyon")
                }
            }
        } catch (Exception e) {
            System.out.println("Tur sozlugu cekilirken hata: " + e.getMessage());
        }
        return genreMap;
    }

    // 2. ANA METOT (Guncellendi)
    public void fetchPopularMovies() {
        String url = baseUrl + "/movie/popular?api_key=" + apiKey + "&language=tr-TR&page=1";

        System.out.println("TMDb'den veriler guncelleniyor...");

        try {
            // Once tur sozlugunu internetten indirip hazirda tutuyoruz
            Map<Integer, String> genreDictionary = fetchGenreDictionary();

            TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);

            if (response != null && response.getResults() != null) {

                // ESKİ movieRepository.deleteAll(); SATIRINI TAMAMEN SİL!
                System.out.println("TMDb'den gelen filmler kontrol ediliyor...");

                int eklenenFilmSayisi = 0;

                for (TmdbMovie tmdbMovie : response.getResults()) {

                    // KONTROL NOKTASI: Bu film zaten veritabanimizda var mi?
                    if (!movieRepository.existsByTitle(tmdbMovie.getTitle())) {

                        // Yoksa, yeni film olarak hazirla ve kaydet
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
                        System.out.println("Yeni Film Eklendi: " + newMovie.getTitle());
                    }
                }
                System.out.println("Islem tamamlandi. Toplam yeni eklenen film: " + eklenenFilmSayisi);
            }

        } catch (Exception e) {
            System.out.println("Bir hata olustu: " + e.getMessage());
        }
    }
}