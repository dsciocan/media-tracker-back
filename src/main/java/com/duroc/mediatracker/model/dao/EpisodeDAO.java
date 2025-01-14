package com.duroc.mediatracker.model.dao;

import com.duroc.mediatracker.ExternalApiConfig;
import com.duroc.mediatracker.model.episode_search.EpisodeSearchResult;
import com.duroc.mediatracker.model.show_search.ShowSearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EpisodeDAO {
    public static ExternalApiConfig apiKey = new ExternalApiConfig();

    public static EpisodeSearchResult requestEpisodesBySeason(long showId, int seasonNumber) throws IOException, InterruptedException {
        String url = String.format("https://api.themoviedb.org/3/tv/%s/season/%d?language=en-US", showId, seasonNumber);
        EpisodeSearchResult results;
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", String.format("Bearer %s", apiKey.getApiKey()))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        results = mapper.readValue(response.body(), EpisodeSearchResult.class);
        return results;
    }
}
