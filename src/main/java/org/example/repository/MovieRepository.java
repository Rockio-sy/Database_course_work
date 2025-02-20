package org.example.repository;

import org.example.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a Movie
    public void addMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, genre, release_date, director, rating) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getGenre(), movie.getReleaseDate(), movie.getDirector(), movie.getRating());
    }

    // Get All Movies
    public List<Movie> getAllMovies() {
        String sql = "SELECT * FROM movies";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Movie(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getDate("release_date").toLocalDate(),
                rs.getString("director"),
                rs.getDouble("rating")
        ));
    }

    // Get a Movie by ID
    public Movie getMovieById(Long movieId) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{movieId}, (rs, rowNum) -> new Movie(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getDate("release_date").toLocalDate(),
                    rs.getString("director"),
                    rs.getDouble("rating")
            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Update a Movie
    public void updateMovie(Movie movie) {
        String sql = "UPDATE movies SET title = ?, genre = ?, release_date = ?, director = ?, rating = ? WHERE id = ?";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getGenre(), movie.getReleaseDate(), movie.getDirector(), movie.getRating(), movie.getId());
    }

    // Delete a Movie
    public void deleteMovie(Long movieId) {
        String sql = "DELETE FROM movies WHERE id = ?";
        jdbcTemplate.update(sql, movieId);
    }
}
