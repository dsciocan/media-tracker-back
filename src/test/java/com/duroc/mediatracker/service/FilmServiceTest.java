package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.dao.FilmDAO;
import com.duroc.mediatracker.model.film_details.FilmDetails;
import com.duroc.mediatracker.model.film_details.Genre;
import com.duroc.mediatracker.model.film_details.ProductionCompany;
import com.duroc.mediatracker.model.film_search.Result;
import com.duroc.mediatracker.model.film_search.FilmSearchResults;
import com.duroc.mediatracker.model.info.Film;
import com.duroc.mediatracker.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class FilmServiceTest {

    @Mock
    FilmRepository filmRepository;

    @Mock
    private FilmDAO filmDAO;

    @InjectMocks
    FilmServiceImplementation filmServiceImplementation;

    public FilmServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

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

    @Test
    void testGetFilmDetails() throws IOException, InterruptedException {
        Long movieId = 123L;
        FilmDetails mockFilmDetails = new FilmDetails(
                List.of(new Genre("Action")),
                "en",
                List.of("US"),
                "An action-packed adventure.",
                "/sample_poster_path.jpg",
                List.of(new ProductionCompany("Sample Production")),
                "2023-01-01",
                120,
                "Sample Movie"
        );

        // Mocking the DAO call
        when(filmDAO.filmSearchDetails(movieId)).thenReturn(mockFilmDetails);

        // Act
        FilmDetails actualDetails = filmServiceImplementation.getFilmDetails(movieId);

        // Assert
        assertAll(()->{
            assertEquals("Sample Movie", actualDetails.title());
            assertEquals("An action-packed adventure.", actualDetails.overview());
            assertEquals("2023-01-01", actualDetails.release_date());
            assertEquals(120, actualDetails.runtime());
            assertEquals("Action", actualDetails.genres().get(0).name());
        });
    }
    @Test
    void testAddFilmToList() throws IOException, InterruptedException {
        // Arrange
        Long movieId = 123L;

        FilmDetails mockFilmDetails = new FilmDetails(
                List.of(new Genre("Action")),
                "en",
                List.of("US"),
                "An action-packed adventure.",
                "/sample_poster_path.jpg",
                List.of(new ProductionCompany("Sample Production")),
                "2023-01-01",
                120,
                "Sample Movie"
        );

        Film savedFilm = Film.builder().id(1L).title("Sample Movie").synopsis("An action-packed adventure.").releaseYear(2023).duration(120).genres(List.of("Action"))
                .productionCompanies(List.of("Sample Production")).language("en").country("US").poster_url("/sample_poster_path.jpg").build();

        when(filmDAO.filmSearchDetails(movieId)).thenReturn(mockFilmDetails);
        when(filmRepository.save(any(Film.class))).thenReturn(savedFilm);

        // Act
        Film result = filmServiceImplementation.addFilmToList(movieId);

        // Assert
        assertNotNull(result);
        assertEquals(savedFilm.getId(), result.getId());
        assertEquals(savedFilm.getTitle(), result.getTitle());
        assertEquals(savedFilm.getSynopsis(), result.getSynopsis());

        verify(filmDAO, times(1)).filmSearchDetails(movieId);
        verify(filmRepository, times(1)).save(any(Film.class));

    }

    @Test
    void testGetFilmById() {
        // Arrange
        Long id = 1L;
        Film savedFilm = Film.builder().id(1L).title("Sample Movie").synopsis("An action-packed adventure.").releaseYear(2023).duration(120).genres(List.of("Action"))
                .productionCompanies(List.of("Sample Production")).language("en").country("US").poster_url("/sample_poster_path.jpg").build();

        when(filmRepository.findById(id)).thenReturn(Optional.of(savedFilm));

        // Act
        Optional<Film> result = filmServiceImplementation.getFilmById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedFilm, result.get());
        verify(filmRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteFilmById() {
        // Arrange
        Long id = 1L;

        when(filmRepository.existsById(id)).thenReturn(true);

        // Act
        filmServiceImplementation.deleteFilmById(id);

        // Assert
        verify(filmRepository, times(1)).existsById(id);
        verify(filmRepository, times(1)).deleteById(id);
    }

}