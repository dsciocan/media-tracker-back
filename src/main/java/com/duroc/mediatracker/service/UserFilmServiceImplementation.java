package com.duroc.mediatracker.service;

import com.duroc.mediatracker.repository.UserFilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFilmServiceImplementation implements UserFilmService{

    @Autowired
    UserFilmRepository userFilmRepository;
}
