package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.show_detail.Genre;
import com.duroc.mediatracker.model.show_detail.ShowDetails;
import com.duroc.mediatracker.model.show_search.Result;
import com.duroc.mediatracker.service.ShowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ShowControllerTest {
    @Mock
    private ShowService showService;

    @InjectMocks
    private ShowController showController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(showController).build();
        mapper = new ObjectMapper();
    }


    @Test
    @DisplayName("GET shows from query works as expected when the service returns an appropriate object")
    public void testGetAllAlbums() throws Exception {

        Result res1 = new Result(1399, "Game of Thrones", "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.", "https://image.tmdb.org/t/p/original/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg", "2011-04-17");
        Result res2 = new Result(206584, "The Game of Thrones Reunion Hosted by Conan O'Brien", "A two-part reunion show with cast members from the final season including Kit Harington, Emilia Clarke, Sophie Turner and more, as well as previously departed fan-favorites like Sean Bean, Jason Momoa, Mark Addy and others.", "https://image.tmdb.org/t/p/original/xwq2TALKkuCujBnUTpr57wDO61y.jpg", "2021-04-05");
        Result res3 = new Result(94997, "House of the Dragon", "The Targaryen dynasty is at the absolute apex of its power, with more than 15 dragons under their yoke. Most empires crumble from such heights. In the case of the Targaryens, their slow fall begins when King Viserys breaks with a century of tradition by naming his daughter Rhaenyra heir to the Iron Throne. But when Viserys later fathers a son, the court is shocked when Rhaenyra retains her status as his heir, and seeds of division sow friction across the realm.", "https://image.tmdb.org/t/p/original/t9XkeE7HzOsdQcDDDapDYh8Rrmt.jpg", "2022-08-21");
        Result res4 = new Result(224372, "A Knight of the Seven Kingdoms: The Hedge Knight", "Set in an age when the Targaryen line still holds the Iron Throne and the memory of the last dragon has not yet passed from living memory, great destinies, powerful foes and dangerous exploits all await these improbable and incomparable friends.", "https://image.tmdb.org/t/p/original/7Pb4gk51oUKWarOhvaSgf5RekIj.jpg", "");
        List<Result> expectedResult = List.of(res1, res2, res3, res4);


        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/mediatracker/shows/search/game%20of%20thrones"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET show details works as expected when the service returns an appropriate object")
    public void testGetShowDetails() throws Exception {

        ShowDetails sampleShowDetails = new ShowDetails("2000", List.of(new Genre("genre")),
                9999999L, true, "2020", "Test", 200, 10, List.of("US"), "en", "Test show", "sdjksdjhfk");
        Mockito.when(showService.getShowDetails(9999999L)).thenReturn(sampleShowDetails);


        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/mediatracker/shows/details/9999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.in_production").value(true))
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("POST save show details works as expected when the service returns an appropriate object")
    public void testSaveShowDetails() throws Exception {

        Show sampleShow = new Show(1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));
        Mockito.when(showService.saveShowDetails(9999999L)).thenReturn(sampleShow);


        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/mediatracker/shows/save").contentType(MediaType.APPLICATION_JSON).content("9999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfEpisodes").value(200))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET saved show details works as expected when the service returns an appropriate object")
    public void testGetSavedShowById() throws Exception {

        Show sampleShow = new Show(1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));

        Mockito.when(showService.getSavedShow(1L)).thenReturn(sampleShow);



        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/mediatracker/shows/saved/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfEpisodes").value(200))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("DELETE saved show works as expected when the service returns an appropriate response")
    public void testDeleteSavedShowById() throws Exception {

        Show sampleShow = new Show(1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200, "US", "en", List.of(new Episode()));

        Mockito.when(showService.deleteShowFromDb(1L)).thenReturn("Success");

        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/mediatracker/shows/1"))
                .andExpect(status().isOk());
    }

}