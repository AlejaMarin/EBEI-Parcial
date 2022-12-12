package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.MovieEntity;
import com.dh.catalog.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class NewMovieConsumer {

    private static final Logger log = LoggerFactory.getLogger(NewMovieConsumer.class);
    private final MovieRepository movieRepository;

    public NewMovieConsumer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void execute(NewMovieConsumer.NewMovieDto data) {
        MovieEntity nuevaPelicula = new MovieEntity();
        BeanUtils.copyProperties(data, nuevaPelicula);
        //movieRepository.deleteById(data.getMovie().getId());
        log.info("Received message... Adding new movie: " + nuevaPelicula);
        movieRepository.save(nuevaPelicula);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewMovieDto implements Serializable{
        private String id;
        private String name;
        private String genre;
        private String urlStream;
    }
}