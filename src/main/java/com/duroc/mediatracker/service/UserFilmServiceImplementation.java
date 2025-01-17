package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.info.Film;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserFilm;
import com.duroc.mediatracker.model.user.UserFilmId;
import com.duroc.mediatracker.repository.UserFilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserFilmServiceImplementation implements UserFilmService{

    @Autowired
    UserFilmRepository userFilmRepository;

    @Autowired
    FilmService filmService;

    @Autowired
    UserService userService;

    @Override
    public UserFilm saveUserFilm(UserFilm userFilm, Long userId, Long movieId) throws IOException, InterruptedException {
        AppUser appUser = userService.getUserById(userId);
        Film film = filmService.addFilmToList(movieId);

        //set the composite key
        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(appUser);
        userFilmId.setFilm(film);
        userFilm.setUserFilmId(userFilmId);

        return userFilmRepository.save(userFilm);
    }

    @Override
    public List<UserFilm> getAllUserFilms(Long userId) {
        AppUser user = userService.getUserById(userId);
        return userFilmRepository.findByUserFilmIdAppUser(user);
    }
}
