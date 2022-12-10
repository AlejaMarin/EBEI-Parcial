package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SeriesServiceClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieServiceClient movieServiceClient;
	private final SeriesServiceClient seriesServiceClient;

	public CatalogController(MovieServiceClient movieServiceClient, SeriesServiceClient seriesServiceClient) {
		this.movieServiceClient = movieServiceClient;
		this.seriesServiceClient = seriesServiceClient;
	}

	@GetMapping("/{genre}")
	ResponseEntity<CatalogResponse> getGenre(@PathVariable String genre) {
		List<MovieServiceClient.MovieDto> peliculas = movieServiceClient.getMovieByGenre(genre);
		List<SeriesServiceClient.SerieDto> series = seriesServiceClient.getSerieByGenre(genre);
		CatalogResponse catalogResponse = new CatalogResponse(peliculas,series);
		return ResponseEntity.ok(catalogResponse);
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	static class CatalogResponse {

		private List<MovieServiceClient.MovieDto> movies = new ArrayList<>();
		private List<SeriesServiceClient.SerieDto> series = new ArrayList<>();
	}
}
