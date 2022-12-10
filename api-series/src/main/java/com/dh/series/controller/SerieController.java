package com.dh.series.controller;

import com.dh.series.model.Serie;
import com.dh.series.service.SerieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Serie serie) {
        serieService.save(serie);
        return ResponseEntity.ok(serie.getId());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity update(@RequestBody Serie serie) {
        serieService.update(serie);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Serie>> getAll() {
        return ResponseEntity.ok(serieService.getAll());
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Serie> getById(@PathVariable String id) {
        return ResponseEntity.ok(serieService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity delete(@PathVariable String id) {
        serieService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<Serie>> getSerieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok().body(serieService.findByGenre(genre));
    }
}