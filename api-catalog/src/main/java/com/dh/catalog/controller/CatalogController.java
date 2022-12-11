package com.dh.catalog.controller;

import com.dh.catalog.service.CatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{genre}")
    @ResponseStatus(code = HttpStatus.OK)
    ResponseEntity<CatalogService.CatalogResponse> getGenre(@PathVariable String genre) throws Exception {
        return ResponseEntity.ok(catalogService.getMoviesAndSeriesByGenre(genre));
    }
}
