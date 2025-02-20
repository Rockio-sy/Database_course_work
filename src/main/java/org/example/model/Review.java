package org.example.model;

import java.sql.Timestamp;

public class Review {

    private Long id;
    private Long clientId;
    private Long movieId;
    private String review;
    private java.sql.Timestamp createdAt;

    // Constructor for creating a review (without ID for new reviews)
    public Review(Long clientId, Long movieId, String review) {
        this.clientId = clientId;
        this.movieId = movieId;
        this.review = review;
    }

    // Constructor for all fields (including ID for retrieval or update)
    public Review(Long id, Long clientId, Long movieId, String review, java.sql.Timestamp createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.movieId = movieId;
        this.review = review;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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

        return bold + "Review{" + reset +
                bold + red + " id=" + reset + cyan + id + reset +
                bold + green + ", clientId=" + reset + yellow + clientId + reset +
                bold + blue + ", movieId=" + reset + purple + movieId + reset +
                bold + green + ", review='" + reset + yellow + review + '\'' + reset +
                bold + red + " }" + reset;
    }
}
