package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.Status;
import com.duroc.mediatracker.model.user.UserFilm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UserFilmService {

    UserFilm saveUserFilm(UserFilm userFilm, Long userId, Long movieId) throws IOException, InterruptedException;

    List<UserFilm> getAllUserFilms(Long userId);

    UserFilm updateUserFilm(UserFilm userFilm, Long userId, Long movieDbId);

    UserFilm getUserFilmById(Long userId, Long filmDbId);

    void deleteUserFilmById(Long userId, Long filmDbId);

    List<UserFilm> getUserFilmsByStatus(Long userId, Status status);

    int getUserFilmRuntime(Long userId);

    HashMap<String, Integer> getStatsForFilmGenres(Long userId);
}
