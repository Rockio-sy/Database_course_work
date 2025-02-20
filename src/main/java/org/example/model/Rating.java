package org.example.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Rating {

    private Long id;
    private Long clientId;
    private Long movieId;
    private BigDecimal rating;
    private java.sql.Timestamp createdAt;

    // Constructor for creating a rating (without ID for new ratings)
    public Rating(Long clientId, Long movieId, BigDecimal rating) {
        this.clientId = clientId;
        this.movieId = movieId;
        this.rating = rating;
    }

    // Constructor for all fields (including ID for retrieval or update)
    public Rating(Long id, Long clientId, Long movieId, BigDecimal rating, java.sql.Timestamp createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.movieId = movieId;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

        return bold + "Rating{" + reset +
                bold + red + " id=" + reset + cyan + id + reset +
                bold + green + ", clientId=" + reset + yellow + clientId + reset +
                bold + blue + ", movieId=" + reset + purple + movieId + reset +
                bold + green + ", rating=" + reset + yellow + rating + reset +
                bold + red + " }" + reset;
    }
}
