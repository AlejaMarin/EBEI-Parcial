package com.dh.series.event;

import com.dh.series.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewSerieProducer {

    private static final Logger log = LoggerFactory.getLogger(NewSerieProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public NewSerieProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(NewSerieData data) {
        log.info("Sending message... Creating new serie...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_NEW_SERIE, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewSerieData {
        private String id;
        private String name;
        private String genre;
        private List<NewSerieData.SeasonDto> seasons = new ArrayList<>();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        static class SeasonDto {

            private Long id;
            private Integer seasonNumber;
            private List<SeasonDto.ChapterDto> chapters = new ArrayList<>();

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            class ChapterDto {

                private Long id;
                private String name;
                private Integer number;
                private String urlStream;
            }
        }
    }
}