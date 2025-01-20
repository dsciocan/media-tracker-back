package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.info.Film;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.Status;
import com.duroc.mediatracker.model.user.UserFilm;
import com.duroc.mediatracker.model.user.UserFilmId;
import com.duroc.mediatracker.repository.UserFilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class UserFilmServiceTest {

    @Mock
    UserFilmRepository userFilmRepository;

    @Mock
    UserService userService;

    @Mock
    FilmService filmService;

    @InjectMocks
    UserFilmServiceImplementation userFilmService;

    private AppUser mockUser;
    private Film mockFilm;
    private UserFilm mockUserFilm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new AppUser();
        mockUser.setId(1L);
//        mockUser.setUsername("testUser");

        mockFilm = new Film();
        mockFilm.setId(1L);
        mockFilm.setTitle("Test Film");

        mockUserFilm = new UserFilm();
        UserFilmId userFilmId = new UserFilmId();
        userFilmId.setAppUser(mockUser);
        userFilmId.setFilm(mockFilm);
        mockUserFilm.setUserFilmId(userFilmId);
        mockUserFilm.setRating(5);
        mockUserFilm.setNotes("Great movie!");
        mockUserFilm.setStatus(Status.WATCHED);
        mockUserFilm.setWatchedDate(LocalDate.now());
    }

    @Test
    void saveUserFilm() throws IOException, InterruptedException {
        when(userService.getUserById(1L)).thenReturn(mockUser);
        when(filmService.addFilmToList(1L)).thenReturn(mockFilm);
        when(userFilmRepository.save(mockUserFilm)).thenReturn(mockUserFilm);

        UserFilm result = userFilmService.saveUserFilm(mockUserFilm, 1L, 1L);

        assertNotNull(result);
        assertEquals(5, result.getRating());
        assertEquals(mockUser, result.getUserFilmId().getAppUser());
        assertEquals(mockFilm, result.getUserFilmId().getFilm());

        verify(userService, times(1)).getUserById(1L);
        verify(filmService, times(1)).addFilmToList(1L);
        verify(userFilmRepository, times(1)).save(mockUserFilm);
    }

    @Test
    void getAllUserFilms() {
        when(userService.getUserById(1L)).thenReturn(mockUser);
        when(userFilmRepository.findByUserFilmIdAppUser(mockUser)).thenReturn(List.of(mockUserFilm));

        List<UserFilm> result = userFilmService.getAllUserFilms(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Great movie!", result.get(0).getNotes());

        verify(userFilmRepository, times(1)).findByUserFilmIdAppUser(mockUser);
    }

    @Test
    void updateUserFilm() {
        when(userService.getUserById(1L)).thenReturn(mockUser);
        when(filmService.getFilmById(1L)).thenReturn(Optional.of(mockFilm));
        when(userFilmRepository.findById(mockUserFilm.getUserFilmId())).thenReturn(Optional.of(mockUserFilm));
        when(userFilmRepository.save(mockUserFilm)).thenReturn(mockUserFilm);

        mockUserFilm.setNotes("Updated notes");
        UserFilm result = userFilmService.updateUserFilm(mockUserFilm, 1L, 1L);

        assertNotNull(result);
        assertEquals("Updated notes", result.getNotes());
        assertEquals(mockUser, result.getUserFilmId().getAppUser());

        verify(userFilmRepository, times(1)).save(mockUserFilm);
    }

    @Test
    void getUserFilmById() {
        when(userService.getUserById(1L)).thenReturn(mockUser);
        when(filmService.getFilmById(1L)).thenReturn(Optional.of(mockFilm));
        when(userFilmRepository.findById(mockUserFilm.getUserFilmId())).thenReturn(Optional.of(mockUserFilm));

        UserFilm result = userFilmService.getUserFilmById(1L, 1L);

        assertNotNull(result);
        assertEquals("Test Film", result.getUserFilmId().getFilm().getTitle());
        verify(userFilmRepository, times(1)).findById(mockUserFilm.getUserFilmId());
    }

    @Test
    void deleteUserFilmById() {
        when(userService.getUserById(1L)).thenReturn(mockUser);
        when(filmService.getFilmById(1L)).thenReturn(Optional.of(mockFilm));

        userFilmService.deleteUserFilmById(1L, 1L);

        verify(userFilmRepository, times(1)).deleteById(mockUserFilm.getUserFilmId());
    }

    @Test
    void getUserFilmsByStatus() {
        when(userService.getUserById(1L)).thenReturn(mockUser);
        when(userFilmRepository.findByUserFilmIdAppUser(mockUser)).thenReturn(List.of(mockUserFilm));

        List<UserFilm> result = userFilmService.getUserFilmsByStatus(1L, Status.WATCHED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Status.WATCHED, result.get(0).getStatus());

        verify(userFilmRepository, times(1)).findByUserFilmIdAppUser(mockUser);
    }
}