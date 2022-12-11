package com.dh.catalog.event;

import com.dh.catalog.client.SeriesServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.SerieEntity;
import com.dh.catalog.repository.SerieRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class NewSerieConsumer {

    private final SerieRepository serieRepository;

    public NewSerieConsumer(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void execute(NewSerieConsumer.Data data) {
        SerieEntity nuevaSerie = new SerieEntity();
        BeanUtils.copyProperties(data.getSerie(), nuevaSerie);
        serieRepository.deleteById(data.getSerie().getId());
        serieRepository.save(nuevaSerie);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        private SeriesServiceClient.SerieDto serie = new SeriesServiceClient.SerieDto();
    }
}
