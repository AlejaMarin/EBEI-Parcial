package com.dh.catalog.repository;

import com.dh.catalog.model.SerieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends MongoRepository<SerieEntity, String> {
}