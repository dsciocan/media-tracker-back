package com.duroc.mediatracker.model.dao;

import com.duroc.mediatracker.ExternalApiConfig;
import com.duroc.mediatracker.model.film_details.FilmDetails;
import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FilmDAO {
    public static FilmSearchResults requestData(String query) throws IOException, InterruptedException {
        String url = String.format("https://api.themoviedb.org/3/search/movie?query=%s&include_adult=false&language=en-US&page=1", query.replace(" ", "%20"));
        ExternalApiConfig apiKey = new ExternalApiConfig();
        FilmSearchResults results;
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", String.format("Bearer %s", apiKey.getApiKey()))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        results = mapper.readValue(response.body(), FilmSearchResults.class);
        return results;
    }

    public static FilmDetails filmSearchDetails(Long movieID) throws IOException, InterruptedException {
        String url = String.format("https://api.themoviedb.org/3/movie/%d?language=en-US", movieID);
        ExternalApiConfig apiKey = new ExternalApiConfig();
        FilmDetails results;
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", String.format("Bearer %s", apiKey.getApiKey()))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        results = mapper.readValue(response.body(), FilmDetails.class);
        return results;
    }
}
