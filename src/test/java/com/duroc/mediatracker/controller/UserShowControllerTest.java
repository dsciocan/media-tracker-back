package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserShow;
import com.duroc.mediatracker.model.user.UserShowId;
import com.duroc.mediatracker.service.UserService;
import com.duroc.mediatracker.service.UserShowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserShowControllerTest {

    @Mock
    private UserShowService userShowService;

    @InjectMocks
    private UserController userShowController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userShowController).build();
        mapper = new ObjectMapper();
    }


    @Test
    void getShowSearchResults() throws Exception {


        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "Watching", 0, LocalDate.now(), null);
        List<UserShow> sampleList = List.of(userShow);

        Mockito.when(userShowService.getAllShowsFromUserList()).thenReturn(sampleList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/mediatracker/users/shows/"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userShowId.show.title").value("test"))
                .andExpect(status().isOk()
                );
    }

    @Test
    void saveShowToUserList() throws Exception {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "Watching", 0, null, null);
        String json = mapper.writeValueAsString(userShow);

        Mockito.when(userShowService.saveShowToUserList(userShow,  123L)).thenReturn(userShow);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/mediatracker/users/shows/save/123").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()
                );
    }

    @Test
    void getUserShowByShowId() throws Exception {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "Watching", 0, null, null);

        Mockito.when(userShowService.getUserShowByShowId(1L)).thenReturn(userShow);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/mediatracker/users/shows/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userShowId.show.title").value("test"))
                .andExpect(status().isOk()
                );
    }

    @Test
    void getUserShowsByWatchStatusAndOptionalGenre() throws Exception {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "Watching", 0, null, null);
        List<UserShow> sampleList = List.of(userShow);

        Mockito.when(userShowService.getUserShowsByWatchStatusAndOptionalGenre("Watching", "genre")).thenReturn(sampleList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/mediatracker/users/shows?status=Watching&genre=genre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userShowId.show.title").value("test"))
                .andExpect(status().isOk()
                );
    }

    @Test
    void changeUserShowDetails() throws Exception {
        AppUser user = new AppUser(1L, "1234");
        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        UserShowId userShowId = new UserShowId(user, sampleShow);
        UserShow userShow = new UserShow(userShowId, 5, "Note 1", "Watching", 0,  null, null);
        UserShow newShow = new UserShow(null, 4, "Different note", "Watched", 200, null, null);

        UserShow resultShow = new UserShow(userShowId, 4, "Different note", "Watched", 200, LocalDate.now(), LocalDate.now());

        String json = mapper.writeValueAsString(newShow);

        Mockito.when(userShowService.changeUserShowDetails(1L, newShow)).thenReturn(resultShow);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/mediatracker/users/shows/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userShowId.show.title").value("test"))
                .andExpect(status().isOk()
                );
    }
}