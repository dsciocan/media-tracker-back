package com.duroc.mediatracker.model.episode_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeSearchResult(List<Result> episodes) {
}
