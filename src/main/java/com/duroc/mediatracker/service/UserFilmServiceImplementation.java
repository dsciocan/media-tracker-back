package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.InvalidItemException;
import com.duroc.mediatracker.model.user.UserFilm;
import com.duroc.mediatracker.repository.FilmRepository;
import com.duroc.mediatracker.repository.UserFilmRepository;
import com.duroc.mediatracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFilmServiceImplementation implements UserFilmService{

    @Autowired
    UserFilmRepository userFilmRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserFilm saveUserFilm(UserFilm userFilm) {
        Long filmId = userFilm.getUserFilmId().getFilm().getId();
        Long userId = userFilm.getUserFilmId().getAppUser().getId();
        if(!filmRepository.existsById(filmId)){
            throw new InvalidItemException("Invalid film ID: " + filmId);
        }
        if(!userRepository.existsById(userId)){
            throw new InvalidItemException("Invalid user ID: " + userId);
        }
        return userFilmRepository.save(userFilm);
    }
}
