package org.example.service;

import org.example.model.Movie;
import org.example.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    // Add a Movie to Watchlist
    public void addMovieToWatchlist(Long clientId, Long movieId) {
        watchlistRepository.addMovieToWatchlist(clientId, movieId);
    }

    // Get Watchlist for a Client
    public List<Movie> getWatchlistByClientId(Long clientId) {
        return watchlistRepository.getWatchlistByClientId(clientId);
    }

    // Remove a Movie from Watchlist
    public void removeMovieFromWatchlist(Long clientId, Long movieId) {
        watchlistRepository.removeMovieFromWatchlist(clientId, movieId);
    }
}
