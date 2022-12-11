package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SeriesServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    private final MovieServiceClient movieServiceClient;
    private final SeriesServiceClient seriesServiceClient;

    public CatalogService(MovieServiceClient movieServiceClient, SeriesServiceClient seriesServiceClient) {
        this.movieServiceClient = movieServiceClient;
        this.seriesServiceClient = seriesServiceClient;
    }

    @CircuitBreaker(name = "movie-series", fallbackMethod = "getMoviesAndSeriesByGenreFallback")
    @Retry(name = "movie-series")
    public CatalogResponse getMoviesAndSeriesByGenre(String genre) throws Exception {
        List<MovieServiceClient.MovieDto> peliculas = movieServiceClient.getMovieByGenre(genre);
        List<SeriesServiceClient.SerieDto> series = seriesServiceClient.getSerieByGenre(genre);
        CatalogResponse catalogResponse = new CatalogResponse(peliculas, series);
        if (peliculas == null || series == null) {
            throw new Exception("No se pudo realizar la busqueda por genero");
        }
        return catalogResponse;
    }

    public CatalogResponse getMoviesAndSeriesByGenreFallback(String genre, Throwable t) throws Exception {
        System.out.println("No se pudo realizar la busqueda por genero. Por favor, intente mas tarde.");
        return null;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogResponse {

        private List<MovieServiceClient.MovieDto> movies = new ArrayList<>();
        private List<SeriesServiceClient.SerieDto> series = new ArrayList<>();
    }
}