package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.show_detail.ShowDetails;
import com.duroc.mediatracker.model.show_search.Result;
import com.duroc.mediatracker.repository.ShowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShowServiceTest {

    @Mock
    ShowRepository showRepository;


    @Mock
    EpisodeService episodeService;

    @InjectMocks
    ShowServiceImplementation showServiceImplementation;

    @Test
    @DisplayName("getShowSearchResults responds with a list of results returned by the external api")
    void testGetShowSearchResults() {

        Result res1 = new Result(1399, "Game of Thrones", "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.", "https://image.tmdb.org/t/p/original/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg", "2011-04-17");
        Result res2 = new Result(206584, "The Game of Thrones Reunion Hosted by Conan O'Brien", "A two-part reunion show with cast members from the final season including Kit Harington, Emilia Clarke, Sophie Turner and more, as well as previously departed fan-favorites like Sean Bean, Jason Momoa, Mark Addy and others.", "https://image.tmdb.org/t/p/original/xwq2TALKkuCujBnUTpr57wDO61y.jpg", "2021-04-05");
        Result res3 = new Result(94997, "House of the Dragon", "The Targaryen dynasty is at the absolute apex of its power, with more than 15 dragons under their yoke. Most empires crumble from such heights. In the case of the Targaryens, their slow fall begins when King Viserys breaks with a century of tradition by naming his daughter Rhaenyra heir to the Iron Throne. But when Viserys later fathers a son, the court is shocked when Rhaenyra retains her status as his heir, and seeds of division sow friction across the realm.", "https://image.tmdb.org/t/p/original/t9XkeE7HzOsdQcDDDapDYh8Rrmt.jpg", "2022-08-21");
        Result res4 = new Result(224372, "A Knight of the Seven Kingdoms: The Hedge Knight", "Set in an age when the Targaryen line still holds the Iron Throne and the memory of the last dragon has not yet passed from living memory, great destinies, powerful foes and dangerous exploits all await these improbable and incomparable friends.", "https://image.tmdb.org/t/p/original/7Pb4gk51oUKWarOhvaSgf5RekIj.jpg", "");
        List<Result> expectedResult = List.of(res1, res2, res3, res4);

        assertAll(() -> {
            assertEquals(expectedResult.size(), showServiceImplementation.getShowSearchResults("game of thrones").size());
            assertEquals(expectedResult, showServiceImplementation.getShowSearchResults("game of thrones"));
        });
    }


    @Test
    @DisplayName("getShowDetails responds with the details of that show returned by the external api")
    void testGetShowDetails() throws IOException, InterruptedException {

        ShowDetails showDetails = showServiceImplementation.getShowDetails(1399L);

        assertAll(() -> {
            assertEquals("Game of Thrones", showDetails.getName());
            assertEquals(3,showDetails.getGenres().size());
            assertEquals(73, showDetails.getNumber_of_episodes());
            assertEquals(8, showDetails.getNumber_of_seasons());
        });
    }

    @Test
    @DisplayName("saveShowDetails saves the details of a show returned by the external api to the database")
    void testSaveShowDetails() throws IOException, InterruptedException {

        Show savedShow = showServiceImplementation.saveShowDetails(1399L);

        Show expectedShow = new Show(null, 1399L, "Game of Thrones",
                "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                2011, 2019, true, "https://image.tmdb.org/t/p/original/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg", List.of("Sci-Fi & Fantasy", "Drama", "Action & Adventure"), 8, 73, "US", "en", new ArrayList<>());
        assertAll(() -> {
            assertEquals(expectedShow, savedShow);
        });
    }

    @Test
    @DisplayName("getSavedShow returns the details of previously saved show")
    void testGetSavedShow() throws IOException, InterruptedException {
        Show savedShow = new Show(1L, 1L, "Game of Thrones",
                "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                2011, 2019, true, "https://image.tmdb.org/t/p/original/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg", List.of("Sci-Fi & Fantasy", "Drama", "Action & Adventure"), 8, 73, "US", "en", List.of(new Episode()));

        Mockito.when(showRepository.findById(1L)).thenReturn(Optional.of(savedShow));

        Show retrievedShow = showServiceImplementation.getSavedShow(1L);

                assertAll(() -> {
            assertEquals(retrievedShow, savedShow);
        });
    }
}