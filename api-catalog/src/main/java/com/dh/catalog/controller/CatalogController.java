package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SeriesServiceClient;
import com.dh.catalog.service.CatalogService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	/*private final MovieServiceClient movieServiceClient;
	private final SeriesServiceClient seriesServiceClient;

	public CatalogController(MovieServiceClient movieServiceClient, SeriesServiceClient seriesServiceClient) {
		this.movieServiceClient = movieServiceClient;
		this.seriesServiceClient = seriesServiceClient;
	}*/

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{genre}")
    @ResponseStatus(code = HttpStatus.OK)
    ResponseEntity<CatalogService.CatalogResponse> getGenre(@PathVariable String genre) throws Exception {
		/*List<MovieServiceClient.MovieDto> peliculas = movieServiceClient.getMovieByGenre(genre);
		List<SeriesServiceClient.SerieDto> series = seriesServiceClient.getSerieByGenre(genre);
		CatalogResponse catalogResponse = new CatalogResponse(peliculas,series);
		return ResponseEntity.ok(catalogResponse);*/
        return ResponseEntity.ok(catalogService.getMoviesAndSeriesByGenre(genre));
    }

	/*@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	static class CatalogResponse {

		private List<MovieServiceClient.MovieDto> movies = new ArrayList<>();
		private List<SeriesServiceClient.SerieDto> series = new ArrayList<>();
	}*/
}
