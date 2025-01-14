package com.duroc.mediatracker.service;

import com.duroc.mediatracker.repository.UserEpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEpisodeServiceImplementation implements UserEpisodeService {

    @Autowired
    UserEpisodeRepository userEpisodeRepository;
}
