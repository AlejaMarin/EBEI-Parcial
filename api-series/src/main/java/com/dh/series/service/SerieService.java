package com.dh.series.service;

import com.dh.series.model.Serie;
import com.dh.series.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void save(Serie serie) {
        serieRepository.save(serie);
    }

    public List<Serie> getAll() {
        return serieRepository.findAll();
    }

    public Serie getById(String id) {
        return serieRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        serieRepository.deleteById(id);
    }

    public void update(Serie serie) {
        if (serieRepository.existsById(serie.getId())) {
            serieRepository.save(serie);
        }
    }

    public List<Serie> findByGenre(String genre) {
        return serieRepository.findByGenre(genre);
    }
}