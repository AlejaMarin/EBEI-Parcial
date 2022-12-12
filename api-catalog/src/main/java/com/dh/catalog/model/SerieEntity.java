package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "series")
public class SerieEntity implements Serializable {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String genre;
    @Field
    private List<SerieEntity.Season> seasons = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Season implements Serializable {

        private Long id;
        private Integer seasonNumber;
        private List<Season.Chapter> chapters = new ArrayList<>();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Chapter implements Serializable {

            private Long id;
            private String name;
            private Integer number;
            private String urlStream;

            @Override
            public String toString() {
                return "Chapter{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", number=" + number +
                        ", urlStream='" + urlStream + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Season{" +
                    "id=" + id +
                    ", seasonNumber=" + seasonNumber +
                    ", chapters=" + chapters +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SerieEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", seasons=" + seasons +
                '}';
    }
}