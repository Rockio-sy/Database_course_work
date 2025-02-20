package org.example.service;

import org.example.model.Movie;
import org.example.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Add a Movie
    public void addMovie(Movie movie) {
        movieRepository.addMovie(movie);
    }

    // Get All Movies
    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    // Get a Movie by ID
    public Movie getMovieById(Long movieId) {
        return movieRepository.getMovieById(movieId);
    }

    // Update a Movie
    public void updateMovie(Movie movie) {
        if (movieRepository.getMovieById(movie.getId()) != null) {
            movieRepository.updateMovie(movie);
        } else {
            throw new RuntimeException("Movie not found!");
        }
    }

    // Delete a Movie
    public void deleteMovie(Long movieId) {
        if (movieRepository.getMovieById(movieId) != null) {
            movieRepository.deleteMovie(movieId);
        } else {
            throw new RuntimeException("Movie not found!");
        }
    }
}
