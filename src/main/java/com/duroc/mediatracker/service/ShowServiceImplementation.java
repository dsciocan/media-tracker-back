package com.duroc.mediatracker.service;

import com.duroc.mediatracker.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowServiceImplementation {

    @Autowired
    ShowRepository showRepository;
}
