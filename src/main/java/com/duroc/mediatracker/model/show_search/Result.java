package com.duroc.mediatracker.model.show_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Result(long id, String name, String overview, String poster_path, String first_air_date) {
}
