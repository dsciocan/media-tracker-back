package com.duroc.mediatracker.service;

import com.duroc.mediatracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    UserRepository userRepository;
}
