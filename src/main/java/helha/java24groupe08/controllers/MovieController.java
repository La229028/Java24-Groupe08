package helha.java24groupe08.controllers;

import helha.java24groupe08.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MovieController {
    public static List<Movie> loadFilmData() {
        InputStream inputStream = MovieController.class.getResourceAsStream("/moviedata.json");
        if (inputStream != null) {
            Gson gson = new Gson();
            Type filmListType = new TypeToken<List<Movie>>(){}.getType();
            List<Movie> movies = gson.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), filmListType);
            System.out.println("Le fichier JSON a été chargé depuis les ressources.");
            return movies;
        } else {
            System.out.println("Le fichier JSON n'a pas pu être chargé depuis les ressources.");
            return null;
        }
    }

    public static void main(String[] args) {
        MovieController controller = new MovieController();
        List<Movie> movies = controller.loadFilmData();
        if (movies != null && !movies.isEmpty()) {
            System.out.println("Liste des films :");
            for (Movie movie : movies) {
                System.out.println("Titre du film : " + movie.getTitle());
                System.out.println("Description du film : " + movie.getPlot());//récupération de la description et affichage dans la console (pour plus tard)
            }
        } else {
            System.out.println("Aucun film trouvé dans le fichier JSON.");
        }
    }
}

