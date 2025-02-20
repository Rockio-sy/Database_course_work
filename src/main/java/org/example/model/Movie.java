package org.example.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Movie {

    private Long id;
    private String title;
    private String genre;
    private LocalDate releaseDate; // Use LocalDate for SQL compatibility
    private String director;
    private double rating;
    private java.sql.Timestamp createdAt; // Use java.sql.Timestamp for SQL compatibility
    private java.sql.Timestamp updatedAt;

    public Movie(){}
    public Movie(String title, String genre, LocalDate releaseDate, String director, double rating) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.director = director;
        this.rating = rating;
    }

    public Movie(Long id, String title, String genre, LocalDate releaseDate, String director, double rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.director = director;
        this.rating = rating;
    }

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        // ANSI color codes
        String reset = "\033[0m";  // Reset all styles
        String bold = "\033[1m";   // Bold text
        String red = "\033[31m";   // Red text
        String green = "\033[32m"; // Green text
        String yellow = "\033[33m"; // Yellow text
        String blue = "\033[34m";  // Blue text
        String purple = "\033[35m"; // Purple text
        String cyan = "\033[36m";  // Cyan text

        return bold + "Movie{" + reset +
                bold + red + " id=" + reset + cyan + id + reset +
                bold + green + ", title='" + reset + yellow + title + '\'' + reset +
                bold + blue + ", genre='" + reset + purple + genre + '\'' + reset +
                bold + green + ", releaseDate=" + reset + yellow + releaseDate + reset +
                bold + blue + ", director='" + reset + purple + director + '\'' + reset +
                bold + green + ", rating=" + reset + yellow + rating + reset +
                bold + red + " }" + reset;
    }
}
