package com.dh.catalog.repository;

import com.dh.catalog.model.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<MovieEntity, String> {

    List<MovieEntity> findByGenreIgnoreCase(String genre);
}