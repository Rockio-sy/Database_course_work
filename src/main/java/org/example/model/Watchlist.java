package org.example.model;

import java.sql.Timestamp;

public class Watchlist {

    private Long clientId;
    private Long movieId;
    private java.sql.Timestamp addedOn;

    // Constructor for creating a watchlist relation (without IDs for new mappings)
    public Watchlist(Long clientId, Long movieId) {
        this.clientId = clientId;
        this.movieId = movieId;
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

    public Timestamp getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Timestamp addedOn) {
        this.addedOn = addedOn;
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

        return bold + "Watchlist{" + reset +
                bold + red + " clientId=" + reset + cyan + clientId + reset +
                bold + green + ", movieId=" + reset + yellow + movieId + reset +
                bold + blue + ", addedOn=" + reset + purple + addedOn + reset +
                bold + red + " }" + reset;
    }
}

