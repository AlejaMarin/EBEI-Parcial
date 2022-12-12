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

import java.io.Serializable;
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
        log.info("New Serie: " + data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewSerieData implements Serializable {
        private String id;
        private String name;
        private String genre;
        private List<NewSerieData.SeasonDto> seasons = new ArrayList<>();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SeasonDto implements Serializable {

            private Long id;
            private Integer seasonNumber;
            private List<SeasonDto.ChapterDto> chapters = new ArrayList<>();

            @Override
            public String toString() {
                return "SeasonDto{" +
                        "id=" + id +
                        ", seasonNumber=" + seasonNumber +
                        ", chapters=" + chapters +
                        '}';
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ChapterDto implements Serializable {

                private Long id;
                private String name;
                private Integer number;
                private String urlStream;

                @Override
                public String toString() {
                    return "ChapterDto{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", number=" + number +
                            ", urlStream='" + urlStream + '\'' +
                            '}';
                }
            }
        }

        @Override
        public String toString() {
            return "NewSerieData{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", genre='" + genre + '\'' +
                    ", seasons=" + seasons +
                    '}';
        }
    }
}