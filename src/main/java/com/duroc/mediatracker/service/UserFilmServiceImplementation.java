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
import java.util.HashMap;
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
    public UserFilm saveUserFilm(UserFilm userFilm,Long movieId) throws IOException, InterruptedException {
        AppUser appUser = userService.getUser();
        Film film = filmService.addFilmToList(movieId);

        //set the composite key
        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(appUser);
        userFilmId.setFilm(film);
        userFilm.setUserFilmId(userFilmId);

        if(userFilmRepository.findById(userFilmId).isPresent()) {
            return userFilmRepository.findById(userFilmId).get();
        }

        return userFilmRepository.save(userFilm);
    }

    @Override
    public List<UserFilm> getAllUserFilms() {
        AppUser appUser = userService.getUser();
        return userFilmRepository.findByUserFilmIdAppUser(appUser);
    }

    @Override
    public UserFilm updateUserFilm(UserFilm userFilm, Long filmDbId) {
        AppUser appUser = userService.getUser();

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
    public UserFilm getUserFilmById(Long filmDbId) {
        AppUser appUser = userService.getUser();

        Film film = filmService.getFilmById(filmDbId).orElseThrow(() ->
                new ItemNotFoundException("Film not found"));

        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(appUser);
        userFilmId.setFilm(film);

        return userFilmRepository.findById(userFilmId)
                .orElseThrow(() -> new ItemNotFoundException("UserFilm not found"));
    }

    @Override
    public void deleteUserFilmById(Long filmDbId) {
        AppUser appUser = userService.getUser();

        Film film = filmService.getFilmById(filmDbId).orElseThrow(() ->
                new ItemNotFoundException("Film not found"));

        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(appUser);
        userFilmId.setFilm(film);

        userFilmRepository.deleteById(userFilmId);
    }

    @Override
    public List<UserFilm> getUserFilmsByStatus(Status status) {
        List<UserFilm> allUserFilms = getAllUserFilms();
        return allUserFilms.stream().filter(userFilm -> userFilm.getStatus().equals(status)).toList();
    }

    @Override
    public int getUserFilmRuntime() {
        List<UserFilm> watchedUserFilms = getUserFilmsByStatus(Status.WATCHED);
        return watchedUserFilms.stream().mapToInt(userFilm->userFilm.getUserFilmId().getFilm().getDuration()).sum();
    }

    @Override
    public HashMap<String, Integer> getStatsForFilmGenres() {
        List<UserFilm> watchedUserFilms = getUserFilmsByStatus(Status.WATCHED);
        HashMap<String,Integer> map = new HashMap<>();
        for(UserFilm userFilm : watchedUserFilms) {
            for(String genre:userFilm.getUserFilmId().getFilm().getGenres()){
                if(!map.containsKey(genre)){
                    map.put(genre,1);
                }
                else{
                    map.put(genre,map.get(genre)+1);
                }
            }
        }
        return map;
    }

    @Override
    public boolean isUserFilmAlreadySaved(Long tmdbId) {
        AppUser user = userService.getUser();
        Film film = filmService.getFilmByTmdbId(tmdbId);
        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(user);
        userFilmId.setFilm(film);

        return userFilmRepository.existsById(userFilmId);
    }
}
