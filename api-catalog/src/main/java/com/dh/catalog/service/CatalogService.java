package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SeriesServiceClient;
import com.dh.catalog.model.MovieEntity;
import com.dh.catalog.model.SerieEntity;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final MovieServiceClient movieServiceClient;
    private final SeriesServiceClient seriesServiceClient;
    private final MovieRepository movieRepository;
    private final SerieRepository serieRepository;

    public CatalogService(MovieServiceClient movieServiceClient, SeriesServiceClient seriesServiceClient, MovieRepository movieRepository, SerieRepository serieRepository) {
        this.movieServiceClient = movieServiceClient;
        this.seriesServiceClient = seriesServiceClient;
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
    }

    @CircuitBreaker(name = "movie-series", fallbackMethod = "getMoviesAndSeriesByGenreFallback")
    @Retry(name = "movie-series")
    /*Elijo a Catalog como el servicio mas utilizado y lo adapto para que sea tolerante a fallos*/
    /*Esquema de Resiliencia: Ya que filtrar por genero en Catalog depende de Movies y Series, con la anotación de
    Circuit Breaker, especifico reglas del circuito (properties) y el metodo fallback que se ejecuta
    en caso de que alguno de los Feign falle.*/
    /*ESTO METODO TAMBIEN CORRESPONDE A LO QUE SERIA EL METODO ONLINE*/
    public CatalogResponse getMoviesAndSeriesByGenre(String genre) throws Exception {
        List<MovieServiceClient.MovieDto> peliculas = movieServiceClient.getMovieByGenre(genre);
        List<SeriesServiceClient.SerieDto> series = seriesServiceClient.getSerieByGenre(genre);
        CatalogResponse catalogResponse = new CatalogResponse(peliculas, series);
        if (peliculas == null || series == null) {
            throw new Exception("No se pudo realizar la busqueda por genero");
        }
        return catalogResponse;
    }

    /*El metodo fallback DEBE retornar el mismo tipo de objeto que el metodo "principal", en este caso,
    Solo quiero mostrar un mensaje en consola, y retorno un null.*/
    public CatalogResponse getMoviesAndSeriesByGenreFallback(String genre, Throwable t) throws Exception {
        System.out.println("No se pudo realizar la busqueda por genero. Por favor, intente mas tarde.");
        return null;
    }

    /*Este metodo en vez de hacer uso de Feign, acude a la base de datos, por eso el uso de los Repositorios*/
    public CatalogResponseOffline getCatalogOffline(String genre) {

        List<MovieEntity> peliculas = movieRepository.findByGenreIgnoreCase(genre);
        List<SerieEntity> series = serieRepository.findByGenreIgnoreCase(genre);
        /*Creo mi propia clase response, para evitar conflicto de mapeo entre clases.*/
        CatalogResponseOffline catalogResponseOffline = new CatalogResponseOffline(peliculas, series);
        return catalogResponseOffline;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogResponse {

        private List<MovieServiceClient.MovieDto> movies = new ArrayList<>();
        private List<SeriesServiceClient.SerieDto> series = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogResponseOffline {

        private List<MovieEntity> movies = new ArrayList<>();
        private List<SerieEntity> series = new ArrayList<>();
    }
}