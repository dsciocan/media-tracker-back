package com.duroc.mediatracker.model.film_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record Result(long id, String overview,String poster_path, String release_date, String title) {
}
