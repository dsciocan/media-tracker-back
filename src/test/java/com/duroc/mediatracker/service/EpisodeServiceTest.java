package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.repository.EpisodeRepository;
import com.duroc.mediatracker.repository.ShowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class EpisodeServiceTest {

    @Mock
    EpisodeRepository episodeRepository;

    @InjectMocks
    EpisodeServiceImplementation episodeService;

    @Test
    @DisplayName("getSavedEpisodesByShowId returns all saved episodes of a specific saved show")
    void getSavedEpisodesByShowId() {


        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200,
                "US", "en",
                List.of());
        Episode ep1 = new Episode(1L, sampleShow, "ep1", "description", 1, 1, 20);

        Episode ep2 = new Episode(2L, sampleShow, "ep2", "desc", 1, 2, 30);

        Episode unrelatedEp = new Episode(3L, new Show(), "ep31", "desc", 1, 31, 22);

        List<Episode> episodeList = List.of(ep1, ep2, unrelatedEp);

        List<Episode> expectedResult = List.of(ep1, ep2);
        Mockito.when(episodeRepository.findAll()).thenReturn(episodeList);

        assertAll(() -> {
            assertEquals(expectedResult.size(), episodeService.getSavedEpisodesByShowId(1L).size());
            assertEquals(expectedResult, episodeService.getSavedEpisodesByShowId(1L));
        });

    }

    @Test
    @DisplayName("getSavedEpisodesBySeason returns all saved episodes of a specific season from a saved show")

    void getSavedEpisodesBySeason() {

        Show sampleShow = new Show(1L, 1L, "test", "test",
                2000, 2020, true, "Test",  List.of("genre"), 10, 200,
                "US", "en",
                List.of());
        Episode ep1 = new Episode(1L, sampleShow, "ep1", "description", 1, 1, 20);

        Episode ep2 = new Episode(2L, sampleShow, "ep2", "desc", 1, 2, 30);

        Episode ep31 = new Episode(3L, sampleShow, "ep31", "desc", 4, 31, 22);


        List<Episode> episodeList = List.of(ep1, ep2, ep31);

        List<Episode> expectedResult = List.of(ep1, ep2);

        Mockito.when(episodeRepository.findAll()).thenReturn(episodeList);

        assertAll(() -> {
            assertEquals(expectedResult.size(), episodeService.getSavedEpisodesBySeason(1L, 1).size());
            assertEquals(expectedResult, episodeService.getSavedEpisodesBySeason(1L, 1));
        });
    }
}