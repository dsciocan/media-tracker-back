package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserEpisode;
import com.duroc.mediatracker.model.user.UserShow;
import com.duroc.mediatracker.model.user.UserShowId;
import com.duroc.mediatracker.repository.ShowRepository;
import com.duroc.mediatracker.repository.UserShowRepository;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserShowServiceTest {
    @Mock
    UserShowRepository userShowRepository;

    @Mock
    UserService userService;

    @Mock
    ShowRepository showRepository;

    @Mock
    ShowService showService;

    @Mock
    UserEpisodeService userEpisodeService;

    @InjectMocks
    UserShowServiceImplementation userShowService;

    @Test
    void getAllShowsFromUserList() {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "Watching", 0, LocalDate.now(), null);
        List<UserShow> sampleList = List.of(userShow);
        Mockito.when(userService.getUser()).thenReturn(user);
        Mockito.when(userShowRepository.findByUserShowIdAppUser(user)).thenReturn(sampleList);
        assertEquals(sampleList, userShowService.getAllShowsFromUserList());

    }

    @Test
    void saveShowToUserList() throws IOException, InterruptedException {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(null, 5, "Note 1", "watched", 200, null, null);
        UserShow expected = new UserShow(userShowId, 5, "Note 1", "watched", 200, LocalDate.now(), LocalDate.now());
        Mockito.when(userService.getUser()).thenReturn(user);
        Mockito.when(showService.saveShowDetails(123L)).thenReturn(sampleShow);
        Mockito.when(userShowRepository.save(userShow)).thenReturn(userShow);
        Mockito.when(userEpisodeService.saveAllShowEpisodesAsUserEpisodes(1L)).thenReturn(List.of(new UserEpisode()));
        assertEquals(expected, userShowService.saveShowToUserList(userShow,  123L));

    }

    @Test
    void getUserShowByShowId() {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of());
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "watched", 200, LocalDate.now(), LocalDate.now());
        Mockito.when(userService.getUser()).thenReturn(user);
        Mockito.when(showService.getSavedShow(1L)).thenReturn(sampleShow);
        Mockito.when(userShowRepository.findById(userShowId)).thenReturn(Optional.of(userShow));
        assertEquals(userShow, userShowService.getUserShowByShowId(1L));
    }

    @Test
    void genreValidator() {
        String string1 = null;
        String string2 = "Action & Adventure";
        String string3 = "action";
        assertAll( () -> {
            assertNull(userShowService.genreValidator(string1));
            assertEquals("Action", userShowService.genreValidator(string2));
            assertEquals("action", userShowService.genreValidator(string3));

                }
        );
    }

    @Test
    void getUserShowsByWatchStatusAndOptionalGenre() {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "watched", 200, LocalDate.now(), LocalDate.now());
        Show sampleShow2 = new Show(1L, 1L, "test2", "test2",
                2000, 2020, true, "Test",  List.of("action"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId2 = new UserShowId(user, sampleShow2);
        UserShow userShow2 = new UserShow(userShowId2, 5, "Note 1", "watching", 3, LocalDate.now(), null);
        Show sampleShow3 = new Show(1L, 1L, "test3", "test2",
                2000, 2020, true, "Test",  List.of("action"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId3 = new UserShowId(user, sampleShow3);
        UserShow userShow3 = new UserShow(userShowId3, 5, "Note 1", "watched", 200, LocalDate.now(), null);

        List<UserShow> allShowsList = List.of(userShow, userShow2, userShow3);

        List<UserShow> expected1 = List.of(userShow2);
        List<UserShow> expected2 = List.of(userShow, userShow3);
        List<UserShow> expected3 = List.of(userShow3);

        Mockito.when(userShowService.getAllShowsFromUserList()).thenReturn(allShowsList);

        assertAll( () -> {
                    assertEquals(expected1, userShowService.getUserShowsByWatchStatusAndOptionalGenre("watching", null));
                    assertEquals(expected2, userShowService.getUserShowsByWatchStatusAndOptionalGenre("watched", null));
                    assertEquals(expected3, userShowService.getUserShowsByWatchStatusAndOptionalGenre("watched", "action"));
                }
        );
    }

    @Test
    void changeUserShowDetails() {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "Watching", 0,LocalDate.now(), LocalDate.now());
        UserShow newShow = new UserShow(null, 4, "Different note", "Watched", 200, null, null);

        UserShow expected = new UserShow(userShowId, 4, "Different note", "Watched", 200, LocalDate.now(), LocalDate.now());

        Mockito.when(userService.getUser()).thenReturn(user);
        Mockito.when(showService.getSavedShow(1L)).thenReturn(sampleShow);
        Mockito.when(userShowRepository.findById(userShowId)).thenReturn(Optional.of(userShow));
        System.out.println(userShowService.getUserShowByShowId(1L));
        Mockito.when(userShowRepository.save(userShow)).thenReturn(userShow);


        assertEquals(expected, userShowService.changeUserShowDetails(1L, newShow));

    }
}