package com.duroc.mediatracker.model.dao;

import com.duroc.mediatracker.ExternalApiConfig;
import com.duroc.mediatracker.model.show_search.ShowSearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ShowDAO {

    public static ShowSearchResult requestData(String query) throws IOException, InterruptedException {
        String url = String.format("https://api.themoviedb.org/3/search/tv?query=%s&include_adult=false&language=en-US&page=1", query.replace(" ", "%20"));
        ExternalApiConfig apiKey = new ExternalApiConfig();
        ShowSearchResult results;
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", String.format("Bearer %s", apiKey.getApiKey()))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        results = mapper.readValue(response.body(), ShowSearchResult.class);
        return results;
    }
}
