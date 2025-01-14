package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.info.TvGenres;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShowRepositoryTest {

    @Autowired
    ShowRepository showRepository;

    @Test
    public void testRespository() {

        Show sampleShow = Show.builder().id(1L).title("Show1").releaseYear(2021).genres(List.of(TvGenres.Comedy, TvGenres.Action_Adventure)).build();


        showRepository.save(sampleShow);

        assertEquals(showRepository.findAll().spliterator().getExactSizeIfKnown(), 1);
        assertEquals(showRepository.findById(1L).get(), sampleShow);
    }

}