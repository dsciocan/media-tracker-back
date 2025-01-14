package com.duroc.mediatracker.model.film_details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FilmDetails(String original_language, String overview, String release_date, int runtime, String title) {
}
