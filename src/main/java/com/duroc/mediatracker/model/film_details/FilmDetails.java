package com.duroc.mediatracker.model.film_details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FilmDetails(List<Genre> genres, String original_language,List<String> origin_country, String overview, String poster_path,List<ProductionCompany>production_companies, String release_date, int runtime, String title) {
}
