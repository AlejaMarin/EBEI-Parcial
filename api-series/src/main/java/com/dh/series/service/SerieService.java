package com.dh.series.service;

import com.dh.series.event.NewSerieProducer;
import com.dh.series.model.Serie;
import com.dh.series.repository.SerieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final NewSerieProducer newSerieProducer;

    public SerieService(SerieRepository serieRepository, NewSerieProducer newSerieProducer) {
        this.serieRepository = serieRepository;
        this.newSerieProducer = newSerieProducer;
    }

    public void save(Serie serie) {
        Serie nuevaSerie = serieRepository.save(serie);
        NewSerieProducer.NewSerieData newSerieData = new NewSerieProducer.NewSerieData();
        BeanUtils.copyProperties(serie, newSerieData);
        if (nuevaSerie != null) {
            newSerieProducer.sendMessage(newSerieData);
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