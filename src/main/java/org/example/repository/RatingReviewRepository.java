package org.example.repository;

import org.example.model.RatingReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingReviewRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a Rating
    public void addRating(Long clientId, Long movieId, double rating) {
        String sql = "INSERT INTO ratings (client_id, movie_id, rating) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, clientId, movieId, rating);
    }

    // Add a Review
    public void addReview(Long clientId, Long movieId, String review) {
        String sql = "INSERT INTO reviews (client_id, movie_id, review) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, clientId, movieId, review);
    }

    // Get Ratings and Reviews by Movie ID
    public List<RatingReview> getRatingsAndReviewsByMovieId(Long movieId) {
        String sql = "SELECT r.rating, rev.review, c.name FROM ratings r " +
                "JOIN reviews rev ON r.client_id = rev.client_id " +
                "JOIN clients c ON r.client_id = c.id WHERE r.movie_id = ? AND rev.movie_id = ?";
        return jdbcTemplate.query(sql, new Object[]{movieId, movieId}, (rs, rowNum) -> new RatingReview(
                rs.getDouble("rating"),
                rs.getString("review"),
                rs.getString("name")
        ));
    }

    // Get Ratings and Reviews by Client ID
    public List<RatingReview> getRatingsAndReviewsByClientId(Long clientId) {
        String sql = "SELECT r.rating, rev.review, m.title FROM ratings r " +
                "JOIN reviews rev ON r.client_id = rev.client_id " +
                "JOIN movies m ON r.movie_id = m.id WHERE r.client_id = ? AND rev.client_id = ?";
        return jdbcTemplate.query(sql, new Object[]{clientId, clientId}, (rs, rowNum) -> new RatingReview(
                rs.getDouble("rating"),
                rs.getString("review"),
                rs.getString("title")
        ));
    }
}
