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
 * Classe principale de l'application cinéma JavaFX.
 * Cette classe lance l'interface utilisateur de l'application, initialise la base de données
 * et charge les films à partir d'une liste de titres prédéfinis.
 */

public class IndexApplication extends Application {
    /**
     * Démarre et affiche l'interface utilisateur de l'application.
     * Cette méthode charge la scène principale à partir d'un fichier FXML et affiche les films récupérés.
     *
     * @param stage Le stage principal sur lequel la scène est définie et affichée.
     * @throws IOException Si le chargement du fichier FXML échoue.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexViewController.class.getResource("index.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // Récupérez une liste de titres de films pour le traitement
        String[] movieTitles = {
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
        FilmController.getAndStoreFilmsFromApi(movieTitles);


        stage.setTitle("CINEMA");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Point d'entrée principal pour l'application JavaFX.
     * Cette méthode initialise la base de données et charge les films dans la base de données
     * à partir d'une liste prédéfinie de titres de films.
     *
     * @param args Arguments de ligne de commande passés à l'application.
     */

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            List<Movie> movies = FilmController.loadFilmData();
            if (movies != null) {
                for (Movie movie : movies) {
                    FilmController.insertMoviesIntoDb(movie);
                }
                launch();
            } else {
                System.out.println("La liste des films chargée est nulle. Impossible d'insérer des films dans la base de données.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }
}