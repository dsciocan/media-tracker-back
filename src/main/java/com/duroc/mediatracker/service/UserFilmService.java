package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.UserFilm;

import java.io.IOException;
import java.util.List;

public interface UserFilmService {

    UserFilm saveUserFilm(UserFilm userFilm, Long userId, Long movieId) throws IOException, InterruptedException;

    List<UserFilm> getAllUserFilms(Long userId);
}
