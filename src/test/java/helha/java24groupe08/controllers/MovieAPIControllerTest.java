package helha.java24groupe08.controllers;

import helha.java24groupe08.client.controllers.MovieAPIController;
import helha.java24groupe08.client.models.MovieDBController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieAPIControllerTest {

    private MovieAPIController movieAPIController;

    @BeforeEach
    void setUp() {
        movieAPIController = new MovieAPIController();
    }

    @Test
    void getMovieFromApi() {
        String movieData = movieAPIController.getMovieFromApi("Inception");
        assertNotNull(movieData);
        // Perform further assertions on the movie data if needed
    }

    @Test
    void getAndStoreMoviesFromApi() {
        String[] titles = {"Inception", "The Dark Knight"};
        movieAPIController.getAndStoreMoviesFromApi(titles);
        // Assuming there is no direct method to retrieve movie data from the database in MovieDBController,
        // further assertions on the stored movies cannot be performed directly here
    }

    @Test
    void isValidURL() {
        assertTrue(movieAPIController.isValidURL("http://www.omdbapi.com/?t=Inception&apikey=3703372b"));
        assertFalse(movieAPIController.isValidURL("invalid_url"));
    }


    @Test
    void movieExistsInDb() {
        String movieData = movieAPIController.getMovieFromApi("Inception");
        MovieDBController MovieDBController = new MovieDBController();
        assertTrue(MovieDBController.movieExists(movieData));
    }
}
