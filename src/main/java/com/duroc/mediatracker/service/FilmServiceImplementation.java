package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.dao.FilmDAO;
import com.duroc.mediatracker.model.film_details.FilmDetails;
import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.duroc.mediatracker.model.film_search.Result;
import com.duroc.mediatracker.model.film_details.Genre;
import com.duroc.mediatracker.model.film_details.ProductionCompany;
import com.duroc.mediatracker.model.info.Film;
import com.duroc.mediatracker.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public FilmDetails getFilmDetails(Long movieID) throws IOException, InterruptedException {
        FilmDetails filmDetails = filmDAO.filmSearchDetails(movieID);
        String fullPosterPath = BASE_IMAGE_URL + filmDetails.poster_path();
        return new FilmDetails(filmDetails.genres(), filmDetails.original_language(),filmDetails.origin_country() ,filmDetails.overview(),
                fullPosterPath, filmDetails.production_companies(), filmDetails.release_date(), filmDetails.runtime(), filmDetails.title()
        );
    }

    @Override
    public Film addFilmToList(Long MovieId) throws IOException, InterruptedException {
        if(!filmRepository.findFilmByTmdbId(MovieId).isEmpty()) {
            return filmRepository.findFilmByTmdbId(MovieId).getFirst();
        }
        FilmDetails filmDetails = getFilmDetails(MovieId);
        Film film = Film.builder()
                .tmdbId(MovieId)
                .title(filmDetails.title())
                .synopsis(filmDetails.overview())
                .releaseYear(Integer.parseInt(filmDetails.release_date().split("-")[0]))
                .duration(filmDetails.runtime())
                .genres(filmDetails.genres().stream().map(Genre::name).toList())
                .productionCompanies(filmDetails.production_companies().stream()
                        .map(ProductionCompany::name)
                        .toList())
                .language(filmDetails.original_language())
                .country(filmDetails.origin_country().getFirst())
                .poster_url(filmDetails.poster_path())
                .build();

        return filmRepository.save(film);
    }

    @Override
    public Optional<Film> getFilmById(Long Id) {
        return filmRepository.findById(Id);
    }

    @Override
    public void deleteFilmById(Long Id) {
        if (!filmRepository.existsById(Id)) {
            throw new RuntimeException("Film Not Found");
        }
        filmRepository.deleteById(Id);
    }

    @Override
    public Film getFilmByTmdbId(Long tmdbId) {
        if(!filmRepository.findFilmByTmdbId(tmdbId).isEmpty()) {
            return filmRepository.findFilmByTmdbId(tmdbId).getFirst();
        } else {
            return null;
        }
    }


}
