package helha.java24groupe08.models;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Classe de test pour la classe {@link Movie}.
 * Contient des tests unitaires pour vérifier le fonctionnement des getters et setters de la classe Movie.
 */
class MovieTest {
    /**
     * Teste les setters et les getters de la classe Movie.
     * Vérifie si les valeurs attribuées via les setters sont correctement récupérées par les getters.
     */
    @Test
    void testMovieSettersAndGetters() {
        Movie movie = new Movie();

        // Utiliser les setters
        movie.setTitle("Inception");
        movie.setYear("2010");
        movie.setRated("PG-13");
        movie.setReleased("16 Jul 2010");
        movie.setRuntime("148 min");
        movie.setGenre("Action, Adventure, Sci-Fi");
        movie.setDirector("Christopher Nolan");
        movie.setWriter("Christopher Nolan");
        movie.setActors("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page, Tom Hardy");
        movie.setPlot("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.");
        movie.setLanguage("English, Japanese, French");
        movie.setCountry("USA, UK");
        movie.setAwards("Won 4 Oscars. Another 152 wins & 218 nominations.");

        // Vérifier avec les getters
        assertEquals("Inception", movie.getTitle());
        assertEquals("2010", movie.getYear());
        assertEquals("PG-13", movie.getRated());
        assertEquals("16 Jul 2010", movie.getReleased());
        assertEquals("148 min", movie.getRuntime());
        assertEquals("Action, Adventure, Sci-Fi", movie.getGenre());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals("Christopher Nolan", movie.getWriter());
        assertEquals("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page, Tom Hardy", movie.getActors());
        assertEquals("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.", movie.getPlot());
        assertEquals("English, Japanese, French", movie.getLanguage());
        assertEquals("USA, UK", movie.getCountry());
        assertEquals("Won 4 Oscars. Another 152 wins & 218 nominations.", movie.getAwards());
    }
}