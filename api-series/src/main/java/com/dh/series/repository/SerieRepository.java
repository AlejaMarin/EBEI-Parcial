package com.dh.series.repository;

import com.dh.series.model.Serie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerieRepository extends MongoRepository<Serie, String> {

    /*El IgnoreCase me ayuda a filtrar por genero, sin importar si el endpoint lo ponen en minuscula o mayuscula*/
    List<Serie> findByGenreIgnoreCase(String genre);
}