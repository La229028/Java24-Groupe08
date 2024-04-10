package helha.java24groupe08.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
    }

    @Test
    void testTitle() {
        String title = "Inception";
        movie.setTitle(title);
        assertEquals(title, movie.getTitle());
    }

    @Test
    void testYear() {
        String year = "2010";
        movie.setYear(year);
        assertEquals(year, movie.getYear());
    }

    @Test
    void testRated() {
        String rated = "PG-13";
        movie.setRated(rated);
        assertEquals(rated, movie.getRated());
    }

    @Test
    void testReleased() {
        String released = "16 Jul 2010";
        movie.setReleased(released);
        assertEquals(released, movie.getReleased());
    }

    @Test
    void testRuntime() {
        String runtime = "148 min";
        movie.setRuntime(runtime);
        assertEquals(runtime, movie.getRuntime());
    }

    @Test
    void testGenre() {
        String genre = "Action, Adventure, Sci-Fi";
        movie.setGenre(genre);
        assertEquals(genre, movie.getGenre());
    }

    @Test
    void testDirector() {
        String director = "Christopher Nolan";
        movie.setDirector(director);
        assertEquals(director, movie.getDirector());
    }

    @Test
    void testWriter() {
        String writer = "Christopher Nolan";
        movie.setWriter(writer);
        assertEquals(writer, movie.getWriter());
    }

    @Test
    void testActors() {
        String actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page, Tom Hardy";
        movie.setActors(actors);
        assertEquals(actors, movie.getActors());
    }

    @Test
    void testPlot() {
        String plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.";
        movie.setPlot(plot);
        assertEquals(plot, movie.getPlot());
    }

    @Test
    void testLanguage() {
        String language = "English, Japanese, French";
        movie.setLanguage(language);
        assertEquals(language, movie.getLanguage());
    }

    @Test
    void testCountry() {
        String country = "USA, UK";
        movie.setCountry(country);
        assertEquals(country, movie.getCountry());
    }

    @Test
    void testAwards() {
        String awards = "Won 4 Oscars. Another 152 wins & 204 nominations.";
        movie.setAwards(awards);
        assertEquals(awards, movie.getAwards());
    }

    @Test
    void testPoster() {
        String poster = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg";
        movie.setPoster(poster);
        assertEquals(poster, movie.getPoster());
    }
}