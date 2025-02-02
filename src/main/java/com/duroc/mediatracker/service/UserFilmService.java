package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.Status;
import com.duroc.mediatracker.model.user.UserFilm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UserFilmService {

    UserFilm saveUserFilm(UserFilm userFilm, Long movieId) throws IOException, InterruptedException;

    List<UserFilm> getAllUserFilms();

    UserFilm updateUserFilm(UserFilm userFilm, Long movieDbId);

    UserFilm getUserFilmById(Long filmDbId);

    void deleteUserFilmById(Long filmDbId);

    List<UserFilm> getUserFilmsByStatus(Status status);

    int getUserFilmRuntime();

    HashMap<String, Integer> getStatsForFilmGenres();

    boolean isUserFilmAlreadySaved(Long tmdbId);
}
