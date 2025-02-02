package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.film_details.FilmDetails;
import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.duroc.mediatracker.model.info.Film;

import java.io.IOException;
import java.util.Optional;

public interface FilmService {
    FilmSearchResults getFilmSearchResults(String query) throws IOException, InterruptedException;

    FilmDetails getFilmDetails(Long movieID) throws IOException, InterruptedException;

    Film addFilmToList(Long MovieId) throws IOException, InterruptedException;

    Optional<Film> getFilmById(Long Id);

    void deleteFilmById(Long Id);

    Film getFilmByTmdbId(Long tmdbId);

}
