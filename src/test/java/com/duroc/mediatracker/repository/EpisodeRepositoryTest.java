package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.info.TvGenres;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EpisodeRepositoryTest {

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    ShowRepository showRepository;

    @Test
    public void testRespository() {
        Show sampleShow = Show.builder().title("test").synopsis("test").releaseYear(2024).finishedYear(2024).isComplete(true).genres(List.of("Comedy", "Action")).country("UK").language("English").numberOfEpisodes(8).numberOfEpisodes(1).episodes(null).build();
        showRepository.save(sampleShow);
        Episode sampleEpisode = Episode.builder().title("test").description("test").episodeNumber(1).runtime(100).seasonNumber(1).show(sampleShow).build();

        episodeRepository.save(sampleEpisode);

        assertEquals(episodeRepository.findAll().spliterator().getExactSizeIfKnown(), 1);
        assertEquals(episodeRepository.findById(1L).get(), sampleEpisode);
    }
}