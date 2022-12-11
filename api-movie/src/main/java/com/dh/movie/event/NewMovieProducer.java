package com.dh.movie.event;

import com.dh.movie.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewMovieProducer {

    private static final Logger log = LoggerFactory.getLogger(NewMovieProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public NewMovieProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(NewMovieData data) {
        log.info("Sending message... Creating new movie...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_NEW_MOVIE, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewMovieData {
        private Long movieId;
        private String operationId;
    }
}