package com.dh.series;

import com.dh.series.model.Chapter;
import com.dh.series.model.Season;
import com.dh.series.model.Serie;
import com.dh.series.repository.SerieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EnableMongoRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class ApiSeriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSeriesApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(SerieRepository serieRepository) {

        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        Chapter chapter = new Chapter(null, "Capitulo 1", 1, "www.netflix.com");
        List<Chapter> capitulos = new ArrayList<>();
        capitulos.add(chapter);
        Season season = new Season(null, 1, capitulos);
        List<Season> temporadas = new ArrayList<>();
        temporadas.add(season);

        return (args) -> {
            if (!serieRepository.findAll().isEmpty()) {
                return;
            }

            serieRepository.save(new Serie(uuid.toString(), "Serie 1", "Terror", temporadas));
            serieRepository.save(new Serie(uuid1.toString(), "Serie 2", "Terror", temporadas));
            serieRepository.save(new Serie(uuid2.toString(), "Serie 3", "Comedia", temporadas));
            serieRepository.save(new Serie(uuid3.toString(), "Serie 4", "Ficcion", temporadas));
        };
    }

}