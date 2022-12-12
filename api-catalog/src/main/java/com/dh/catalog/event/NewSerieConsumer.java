package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.SerieEntity;
import com.dh.catalog.repository.SerieRepository;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class NewSerieConsumer {

    private static final Logger log = LoggerFactory.getLogger(NewMovieConsumer.class);
    private final SerieRepository serieRepository;

    public NewSerieConsumer(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void execute(NewSerieConsumer.SerieDto data) {
        SerieEntity nuevaSerie = new SerieEntity();
        BeanUtils.copyProperties(data, nuevaSerie);
        //serieRepository.deleteById(data.getSerie().getId());
        log.info("Received message... Adding new serie: " + nuevaSerie);
        serieRepository.save(nuevaSerie);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SerieDto implements Serializable {

        private String id;
        private String name;
        private String genre;
        private List<SerieDto.SeasonDto> seasons = new ArrayList<>();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SeasonDto implements Serializable {

            private Long id;
            private Integer seasonNumber;
            private List<SeasonDto.ChapterDto> chapters = new ArrayList<>();

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ChapterDto implements Serializable {

                private Long id;
                private String name;
                private Integer number;
                private String urlStream;
            }
        }
    }
}