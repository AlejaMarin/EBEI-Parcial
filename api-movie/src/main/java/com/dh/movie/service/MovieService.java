package com.dh.movie.service;


import com.dh.movie.event.NewMovieProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {


    private final MovieRepository movieRepository;
    private final NewMovieProducer newMovieProducer;

    public MovieService(MovieRepository movieRepository, NewMovieProducer newMovieProducer) {
        this.movieRepository = movieRepository;
        this.newMovieProducer = newMovieProducer;
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie save(Movie movie) {
        Movie nuevaPelicula = movieRepository.save(movie);
        if (nuevaPelicula != null) {
            newMovieProducer.sendMessage(new NewMovieProducer.NewMovieData(movie.getId().toString(),
                    movie.getName(),
                    movie.getGenre(),
                    movie.getUrlStream()));
        }
        return nuevaPelicula;
    }
}
