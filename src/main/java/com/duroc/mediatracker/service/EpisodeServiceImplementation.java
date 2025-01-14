package com.duroc.mediatracker.service;

import com.duroc.mediatracker.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EpisodeServiceImplementation {
    @Autowired
    EpisodeRepository episodeRepository;
}
