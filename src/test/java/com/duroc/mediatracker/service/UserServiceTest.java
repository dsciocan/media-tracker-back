package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.InvalidItemException;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImplementation userService;

    @Test
    void testGetUserById() {
        AppUser user = new AppUser(1L, 34459880L, "someone");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertEquals(user, userService.getUserById(1L));

    }

    @Test
    void testSaveUser() {
        AppUser user = new AppUser(1L, 34459880L, "someone");

        Mockito.when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.saveUser(user));
    }

    @Test
    void testChangeUsername() {

        AppUser user = new AppUser(1L, 34459880L, "someone");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String newUsername = "someone else";
        AppUser modifiedUser = new AppUser(1L, 34459880L, "someone else");
        Mockito.when(userRepository.save(modifiedUser)).thenReturn(modifiedUser);

        assertEquals(modifiedUser, userService.changeUsername(1L, newUsername));
    }

    @Test
    void testChangeUsername_nullUsername() {

        AppUser user = new AppUser(1L, 34459880L, "someone");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String newUsername = "";

        assertThrows(InvalidItemException.class, () -> userService.changeUsername(1L, newUsername));
    }

}