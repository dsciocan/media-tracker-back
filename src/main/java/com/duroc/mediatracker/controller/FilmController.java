package com.duroc.mediatracker.controller;


import com.duroc.mediatracker.model.film_details.FilmDetails;
import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.duroc.mediatracker.model.film_search.Result;
import com.duroc.mediatracker.model.info.Film;
import com.duroc.mediatracker.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mediatracker/films")
public class FilmController {

    @Autowired
    FilmService filmService;

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Result>> getFilmSearchResults(@PathVariable String query) throws IOException, InterruptedException {
        FilmSearchResults filmSearchResults = filmService.getFilmSearchResults(query);
        return new ResponseEntity<>(filmSearchResults.results(), HttpStatus.OK);
    }

    @GetMapping("/details/{movieID}")
    public ResponseEntity<FilmDetails> getFilmDetails(@PathVariable Long movieID) throws IOException, InterruptedException {
        FilmDetails filmDetails = filmService.getFilmDetails(movieID);
        return new ResponseEntity<>(filmDetails,HttpStatus.OK);
    }

    @PostMapping("/save/{movieID}")
    public ResponseEntity<Film> addFilmById(@PathVariable Long movieID) throws IOException, InterruptedException {
        Film savedFilm = filmService.addFilmToList(movieID);
        return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
    }

    @GetMapping("/saved/{Id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long Id) {
        return filmService.getFilmById(Id)
                .map(film->new ResponseEntity<>(film,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<String> deleteFilmById(@PathVariable Long Id) {
        filmService.deleteFilmById(Id);
        return new ResponseEntity<>("Film successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/saved/tmdb={tmdbId}")
    public ResponseEntity<Film> getFilmByTmdbId(@PathVariable Long tmdbId) {
        return new ResponseEntity<>(filmService.getFilmByTmdbId(tmdbId), HttpStatus.OK);
    }

}
