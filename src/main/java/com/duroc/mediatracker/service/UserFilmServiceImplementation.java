package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.ItemNotFoundException;
import com.duroc.mediatracker.model.info.Film;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.Status;
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

    @Override
    public UserFilm updateUserFilm(UserFilm userFilm, Long userId, Long filmDbId) {
        AppUser appUser = userService.getUserById(userId);

        Film film = filmService.getFilmById(filmDbId).orElseThrow(() ->
                new ItemNotFoundException("Film not found"));

        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(appUser);
        userFilmId.setFilm(film);

        UserFilm existingUserFilm = userFilmRepository.findById(userFilmId)
                .orElseThrow(() -> new ItemNotFoundException("UserFilm not found"));

        //update user fields
        existingUserFilm.setNotes(userFilm.getNotes());
        existingUserFilm.setRating(userFilm.getRating());
        existingUserFilm.setStatus(userFilm.getStatus());
        existingUserFilm.setWatchedDate(userFilm.getWatchedDate());

        return userFilmRepository.save(existingUserFilm);
    }

    @Override
    public UserFilm getUserFilmById(Long userId, Long filmDbId) {
        AppUser appUser = userService.getUserById(userId);

        Film film = filmService.getFilmById(filmDbId).orElseThrow(() ->
                new ItemNotFoundException("Film not found"));

        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(appUser);
        userFilmId.setFilm(film);

        return userFilmRepository.findById(userFilmId)
                .orElseThrow(() -> new ItemNotFoundException("UserFilm not found"));
    }

    @Override
    public void deleteUserFilmById(Long userId, Long filmDbId) {
        AppUser appUser = userService.getUserById(userId);

        Film film = filmService.getFilmById(filmDbId).orElseThrow(() ->
                new ItemNotFoundException("Film not found"));

        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(appUser);
        userFilmId.setFilm(film);

        userFilmRepository.deleteById(userFilmId);
    }

    @Override
    public List<UserFilm> getUserFilmsByStatus(Long userId, Status status) {
        List<UserFilm> allUserFilms = getAllUserFilms(userId);
        return allUserFilms.stream().filter(userFilm -> userFilm.getStatus().equals(status)).toList();
    }
}
