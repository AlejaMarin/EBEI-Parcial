package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class MovieEntity implements Serializable {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String genre;
    @Field
    private String urlStream;

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", urlStream='" + urlStream + '\'' +
                '}';
    }
}