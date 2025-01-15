package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.film_search.FilmSearchResults;

import java.io.IOException;

public interface FilmService {
    FilmSearchResults getFilmSearchResults(String query) throws IOException, InterruptedException;
}
