package com.mcp.movieapp.entity;

import jakarta.persistence.*;

@Entity // Spring Boot'a "Bu sınıf sıradan bir class değil, veritabanında bir tablo olacak" der.
@Table(name = "movies") // MySQL'deki tablonun adı 'movies' olsun.
public class Movie {

    @Id // Bu değişkenin tablodaki "Primary Key" (Birincil Anahtar) olduğunu belirtir.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'leri 1, 2, 3 diye otomatik artırır (Auto Increment).
    private Long id;

    private String title;       // Film Adı
    private String director;    // Yönetmen
    private Integer releaseYear; // Çıkış Yılı
    private String genre;       // Türü (Aksiyon, Dram vb.)
    private Double rating;      // Puanı (Örn: 8.5)

    // 1. Boş Constructor (JPA / Spring Boot arka planda nesne üretirken buna ihtiyaç duyar)
    public Movie() {
    }

    // 2. Dolu Constructor (İleride biz elimizle film oluştururken kolaylık sağlar)
    public Movie(String title, String director, Integer releaseYear, String genre, Double rating) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
    }

    // 3. Getter ve Setter Metodları (Encapsulation gereği private değişkenlere erişim için)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}