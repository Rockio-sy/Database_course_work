package org.example.service;

import org.example.model.RatingReview;
import org.example.repository.RatingReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingReviewService {

    @Autowired
    private RatingReviewRepository ratingReviewRepository;

    // Add a Rating
    public void addRating(Long clientId, Long movieId, double rating) {
        ratingReviewRepository.addRating(clientId, movieId, rating);
    }

    // Add a Review
    public void addReview(Long clientId, Long movieId, String review) {
        ratingReviewRepository.addReview(clientId, movieId, review);
    }

    // Get Ratings and Reviews by Movie ID
    public List<RatingReview> getRatingsAndReviewsByMovieId(Long movieId) {
        return ratingReviewRepository.getRatingsAndReviewsByMovieId(movieId);
    }

    // Get Ratings and Reviews by Client ID
    public List<RatingReview> getRatingsAndReviewsByClientId(Long clientId) {
        return ratingReviewRepository.getRatingsAndReviewsByClientId(clientId);
    }
}
