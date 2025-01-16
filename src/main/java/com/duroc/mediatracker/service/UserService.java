package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.AppUser;

public interface UserService {
    AppUser getUserById(Long id);
    AppUser saveUser(AppUser appUser);
    AppUser changeUsername(Long userId, String newUsername);
    String deleteUser(Long userId);
}
