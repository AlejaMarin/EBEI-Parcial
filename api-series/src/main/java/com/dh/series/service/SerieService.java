package com.dh.series.service;

import com.dh.series.event.NewSerieProducer;
import com.dh.series.model.Serie;
import com.dh.series.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final NewSerieProducer newSerieProducer;

    public SerieService(SerieRepository serieRepository, NewSerieProducer newSerieProducer) {
        this.serieRepository = serieRepository;
        this.newSerieProducer = newSerieProducer;
    }

    public void save(Serie serie) {
        String operationId = UUID.randomUUID().toString();
        Serie nuevaSerie = serieRepository.save(serie);
        if (nuevaSerie != null) {
            newSerieProducer.sendMessage(new NewSerieProducer.NewSerieData(serie.getId(), operationId));
            System.out.println(operationId);
        }
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
        return serieRepository.findByGenreIgnoreCase(genre);
    }
}