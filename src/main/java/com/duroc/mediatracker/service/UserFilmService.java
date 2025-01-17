package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.UserFilm;

import java.io.IOException;

public interface UserFilmService {

    UserFilm saveUserFilm(UserFilm userFilm, Long userId, Long movieId) throws IOException, InterruptedException;
}
