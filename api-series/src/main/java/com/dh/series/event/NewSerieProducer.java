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
        private String serieId;
        private String operationId;
    }
}