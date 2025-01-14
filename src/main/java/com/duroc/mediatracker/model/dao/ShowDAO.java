package com.duroc.mediatracker.model.dao;

import com.duroc.mediatracker.ExternalApiConfig;
import com.duroc.mediatracker.model.show_detail.ShowDetails;
import com.duroc.mediatracker.model.show_search.ShowSearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ShowDAO {

    public static ExternalApiConfig apiKey = new ExternalApiConfig();

    public static ShowSearchResult requestShowSearchData(String query) throws IOException, InterruptedException {
        String url = String.format("https://api.themoviedb.org/3/search/tv?query=%s&include_adult=false&language=en-US&page=1", query.replace(" ", "%20"));
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

    public static ShowDetails requestShowDetails(long id) throws IOException, InterruptedException {
        String url = String.format("https://api.themoviedb.org/3/tv/%s?language=en-US", id);
        ShowDetails results;
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", String.format("Bearer %s", apiKey.getApiKey()))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        results = mapper.readValue(response.body(), ShowDetails.class);
        return results;
    }
}
