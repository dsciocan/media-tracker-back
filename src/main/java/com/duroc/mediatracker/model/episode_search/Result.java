package com.duroc.mediatracker.model.episode_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Result(
        String air_date,
        int episode_number,
        String name,
        String overview,
        int runtime,
        int season_number) {
}
