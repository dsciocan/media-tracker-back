package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.AppUser;

import java.util.Map;

public interface UserService {
    AppUser getUserById(Long id);
    AppUser getUser();
    AppUser saveUser(String token);
//    AppUser changeUsername(Long userId, String newUsername);
    String deleteUser();

    Map<String,Integer> getAllByGenre ();

    int totalRuntime();
}
