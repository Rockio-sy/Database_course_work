package org.example.model;

public class RatingReview {
    private double rating;
    private String review;
    private String clientOrMovieName;

    public RatingReview(double rating, String review, String clientOrMovieName) {
        this.rating = rating;
        this.review = review;
        this.clientOrMovieName = clientOrMovieName;
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

        return bold + "Rating: " + reset + yellow + rating + reset +
                bold + " | Review: " + reset + purple + review + reset +
                bold + " | Client/Movie: " + reset + cyan + clientOrMovieName + reset;
    }

    // Getters and setters (if needed)
}
