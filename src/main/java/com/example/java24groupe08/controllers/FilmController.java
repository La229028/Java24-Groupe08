package com.example.java24groupe08.controllers;

import com.example.java24groupe08.models.Film;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FilmController {
    public static List<Film> loadFilmData() {
        InputStream inputStream = FilmController.class.getResourceAsStream("/moviedata.json");
        if (inputStream != null) {
            Gson gson = new Gson();
            Type filmListType = new TypeToken<List<Film>>(){}.getType();
            List<Film> films = gson.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), filmListType);
            System.out.println("Le fichier JSON a été chargé depuis les ressources.");
            return films;
        } else {
            System.out.println("Le fichier JSON n'a pas pu être chargé depuis les ressources.");
            return null;
        }
    }

    public static void main(String[] args) {
        FilmController controller = new FilmController();
        List<Film> films = controller.loadFilmData();
        if (films != null && !films.isEmpty()) {
            System.out.println("Liste des films :");
            for (Film film : films) {
                System.out.println("Titre du film : " + film.getTitle());
                System.out.println("Description du film : " + film.getPlot());//récupération de la description et affichage dans la console (pour plus tard)
            }
        } else {
            System.out.println("Aucun film trouvé dans le fichier JSON.");
        }
    }
}

