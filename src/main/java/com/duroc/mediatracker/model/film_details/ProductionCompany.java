package com.duroc.mediatracker.model.film_details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductionCompany(String name) {
}
