package com.mcp.movieapp.repository;

import com.mcp.movieapp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Bunu eklediğinden emin ol

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Eski yetenegimiz
    boolean existsByTitle(String title);

    // YENI EKLENEN SİHİRLİ METOT: İsime göre arama yeteneği
    List<Movie> findByTitleContainingIgnoreCase(String keyword);

}