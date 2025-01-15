package com.duroc.mediatracker.controller;


import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.duroc.mediatracker.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/mediatracker/films")
public class FilmController {

    @Autowired
    FilmService filmService;

    @GetMapping("/search/{query}")
    public ResponseEntity<FilmSearchResults> getFilmSearchResults(@PathVariable String query) throws IOException, InterruptedException {
        FilmSearchResults filmSearchResults = filmService.getFilmSearchResults(query);
        return new ResponseEntity<>(filmSearchResults, HttpStatus.OK);
    }


}
