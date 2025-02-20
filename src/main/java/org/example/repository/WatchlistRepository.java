package org.example.repository;

import org.example.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class WatchlistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a Movie to Watchlist
    public void addMovieToWatchlist(Long clientId, Long movieId) {
        String sql = "INSERT INTO watchlist (client_id, movie_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, clientId, movieId);
    }

    // Get Watchlist for a Client
    public List<Movie> getWatchlistByClientId(Long clientId) {
        String sql = "SELECT m.* FROM movies m JOIN watchlist w ON m.id = w.movie_id WHERE w.client_id = ?";
        return jdbcTemplate.query(sql, new Object[]{clientId}, (rs, rowNum) -> new Movie(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getObject("release_date", LocalDate.class),
                rs.getString("director"),
                rs.getDouble("rating")
        ));
    }

    // Remove a Movie from Watchlist
    public void removeMovieFromWatchlist(Long clientId, Long movieId) {
        String sql = "DELETE FROM watchlist WHERE client_id = ? AND movie_id = ?";
        jdbcTemplate.update(sql, clientId, movieId);
    }
}
