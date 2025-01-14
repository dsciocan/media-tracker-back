package com.duroc.mediatracker.model.show_detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShowDetails(
                          List<Creator> created_by,
                          List<Integer> episode_run_time,
                          String first_air_date,
                          List<Genre> genres,
                          long id,
                          boolean in_production,
                          String last_air_date,
                          String name,
                          int number_of_episodes,
                          int number_of_seasons,
                          List<String> origin_country,
                          String original_language,
                          String overview) {
}
