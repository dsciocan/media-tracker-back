package com.duroc.mediatracker.model.film_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FilmSearchResults(List<Result> results, int total_results) {
}
