package com.dh.catalog.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "api-series")
public interface SeriesServiceClient {

    @GetMapping("/api/v1/series/{genre}")
    List<SerieDto> getSerieByGenre(@PathVariable(value = "genre") String genre);

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class SerieDto {

        private String id;
        private String name;
        private String genre;
        private List<SeasonDto> seasons = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class SeasonDto {

        private Long id;
        private Integer seasonNumber;
        private List<ChapterDto> chapters = new ArrayList<>();
    }

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