package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.dao.FilmDAO;
import com.duroc.mediatracker.model.film_search.Result;
import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.duroc.mediatracker.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class FilmServiceTest {

    @Mock
    FilmRepository filmRepository;

    @Mock
    private FilmDAO filmDAO;

    @InjectMocks
    FilmServiceImplementation filmServiceImplementation;

    @Test
    void testGetFilmSearchResults() throws IOException, InterruptedException {
        //arrange
        Result mockResult = new Result(
                41393,
                "A married man meets a beautiful woman in a Las Vegas casino and allows her to seduce him. When her jealous boyfriend finds them together, a scuffle results in the boyfriend's death. The lovers head to the desert to bury the corpse, but it disappears.",
                "/63XsqpeRQToLJhlAWQpUG9tSx2Q.jpg",
                "2006-02-25",
                "Zyzzyx Road"
        );
        FilmSearchResults mockSearchResults = new FilmSearchResults(List.of(mockResult), 1);

        when(filmDAO.requestData("Zyzzyx Road")).thenReturn(mockSearchResults);

        // act
        FilmSearchResults result = filmServiceImplementation.getFilmSearchResults("Zyzzyx Road");

        // assert
        assertEquals(1, result.total_results());
        assertEquals("Zyzzyx Road", result.results().get(0).title());
    }

}