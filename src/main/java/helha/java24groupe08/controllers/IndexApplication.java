package helha.java24groupe08.controllers;

import helha.java24groupe08.models.Movie;
import helha.java24groupe08.views.IndexViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

/**
 * This is the main class for the application.
 * It sets up the stage and scene, and loads the movie data.
 */
public class IndexApplication extends Application {

    /**
     * This method starts the application.
     * It sets up the stage and scene, and loads the movie data.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If there is an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexViewController.class.getResource("index.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        String[] movieTitles = getMovieTitles();

        MovieController.getAndStoreMoviesFromApi(movieTitles);

        stage.setTitle("Cinéma");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method returns a list of movie titles.
     *
     * @return A list of movie titles.
     */
    public String[] getMovieTitles() {
        return new String[] {
                "Psycho",
                "Hereditary",
                "The Witch",
                "Midsommar",
                "The Conjuring",
                "Sinister",
                "Insidious",
                "Saw",
                "Halloween",
                "A Nightmare on Elm Street",
                "Friday the 13th",
                "The Texas Chainsaw Massacre",
                "The Shining",
                "Basic Instinct",
                "Fatal Attraction",
                "Se7en",
                "Zodiac",
                "Gone Girl",
                "Black Swan",
                "Eyes Wide Shut",
                "The Silence of the Lambs",
                "Mulholland Drive",
                "Blue Velvet",
                "Antichrist",
                "Raw",
                "It Follows",
                "The Neon Demon",
                "Suspiria",
                "Climax",
                "The Human Centipede",
                "Mother!",
                "Annihilation",
                "The Cabin in the Woods",
                "Get Out",
                "Us",
                "Scream",
                "The Ring",
                "The Babadook",
                "A Quiet Place",
                "The Descent",
                "Carrie",
                "Jaws",
                "Rosemary's Baby",
                "The Omen",
                "Shutter Island",
                "The Departed",
                "Misery",
                "Oldboy",
                "Memento",
                "American Psycho",
                "The Prestige",
                "Donnie Darko",
                "The Machinist",
                "The Others",
                "The Sixth Sense",
                "Nightcrawler",
                "Prisoners",
                "Funny Games",
                "The Strangers",
                "The Purge",
                "You're Next",
                "Hard Candy",
                "I Spit on Your Grave",
                "The Last House on the Left",
                "Eden Lake",
                "Martyrs",
                "High Tension",
                "Wolf Creek",
                "Audition",
                "Irreversible",
                "A Serbian Film",
                "Teeth",
                "Raw",
                "Inception",
                "Matrix",
                "Interstellar",
                "Cars",
                "Greenroom"
        };
    }

    /**
     * The main method of the application.
     * It loads the movie data and launches the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            List<Movie> movies = MovieController.loadMovieData();
            if (movies != null) {
                for (Movie movie : movies) {
                    MovieController.insertMoviesIntoDb(movie);
                }
                launch();
            } else {
                System.out.println("La liste des films chargée est nulle. Impossible d'insérer des films dans la base de données.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}