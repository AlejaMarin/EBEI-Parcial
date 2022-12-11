package com.dh.catalog.event;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.MovieEntity;
import com.dh.catalog.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class NewMovieConsumer {

    private final MovieRepository movieRepository;

    public NewMovieConsumer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void execute(NewMovieConsumer.Data data) {
        MovieEntity nuevaPelicula = new MovieEntity();
        BeanUtils.copyProperties(data.getMovie(), nuevaPelicula);
        movieRepository.deleteById(data.getMovie().getId());
        movieRepository.save(nuevaPelicula);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        private MovieServiceClient.MovieDto movie = new MovieServiceClient.MovieDto();
    }
}
