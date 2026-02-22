package com.mcp.movieapp.repository;

import com.mcp.movieapp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Spring Boot converts this method name into a SQL query automatically!
    // Equivalent SQL: SELECT * FROM movies WHERE LOWER(title) LIKE LOWER('%keyword%')
    List<Movie> findByTitleContainingIgnoreCase(String keyword);

}