package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.InvalidItemException;
import com.duroc.mediatracker.Exception.ItemNotFoundException;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public AppUser getUserById(Long id) {
        if(userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new ItemNotFoundException(String.format("User with id %s not found", id));
        }
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        if(appUser.getUsername().isEmpty()) {
            throw new InvalidItemException("Username cannot be empty");
        } else {
            return userRepository.save(appUser);
        }
    }

    @Override
    public AppUser changeUsername(Long userId, String newUsername) {
        AppUser user = getUserById(userId);
        if(newUsername.isEmpty()) {
            throw new InvalidItemException("Username cannot be empty");
        } else {
            user.setUsername(newUsername);
            return userRepository.save(user);
        }
    }

    @Override
    public String deleteUser(Long userId) {
        AppUser user = getUserById(userId);
        userRepository.delete(user);
        return "User deleted successfully";

    }

}
