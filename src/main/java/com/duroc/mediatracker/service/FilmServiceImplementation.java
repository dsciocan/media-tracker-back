package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.dao.FilmDAO;
import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.duroc.mediatracker.model.film_search.Result;
import com.duroc.mediatracker.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FilmServiceImplementation implements FilmService{

    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original";

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    FilmDAO filmDAO;

    @Override
    public FilmSearchResults getFilmSearchResults(String query) throws IOException, InterruptedException {
        FilmSearchResults searchResults = filmDAO.requestData(query);

        List<Result> updatedResults = searchResults.results().stream()
                .map(result -> new Result(result.id(), result.overview(),
                        BASE_IMAGE_URL + result.poster_path(),
                        result.release_date(), result.title()
                ))
                .toList();

        return new FilmSearchResults(updatedResults, searchResults.total_results());
    }
}
