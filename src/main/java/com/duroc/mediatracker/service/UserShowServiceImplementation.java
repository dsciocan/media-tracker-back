package com.duroc.mediatracker.service;

import com.duroc.mediatracker.repository.UserShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShowServiceImplementation implements  UserShowService {

    @Autowired
    UserShowRepository userShowRepository;
}
