package helha.java24groupe08.controllers;

import helha.java24groupe08.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieControllerTest {

    private MovieController movieController;

    @BeforeEach
    void setUp() {
        movieController = new MovieController();
    }

    @Test
    void getMovieFromApi() {
        Movie movie = movieController.getMovieFromApi("Inception");
        assertNotNull(movie);
        assertEquals("Inception", movie.getTitle());
    }

    @Test
    void getAndStoreMoviesFromApi() {
        String[] titles = {"Inception", "The Dark Knight"};
        movieController.getAndStoreMoviesFromApi(titles);
        List<Movie> movies = movieController.loadMovieData();
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("Inception")));
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("The Dark Knight")));
    }

    @Test
    void isValidURL() {
        assertTrue(movieController.isValidURL("http://www.omdbapi.com/?t=Inception&apikey=3703372b"));
        assertFalse(movieController.isValidURL("invalid_url"));
    }

    @Test
    void loadMovieData() {
        List<Movie> movies = movieController.loadMovieData();
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }

    @Test
    void movieExistsInDb() {
        Movie movie = movieController.getMovieFromApi("Inception");
        assertTrue(movieController.movieExistsInDb(movie));
    }
}