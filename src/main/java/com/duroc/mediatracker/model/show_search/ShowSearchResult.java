package com.duroc.mediatracker.model.show_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShowSearchResult(List<Result> results, int totalResults) {
}
