package com.example.java24groupe08;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void afficherFilm() {
        OMDbApiService apiService = new OMDbApiService("3703372");
        String movieJson = apiService.fetchMovieData("Cars");
        OMDbApiService.Movie movie = apiService.parseMovieData(movieJson);

        // Mettre à jour l'interface utilisateur avec les données du film
        labelTitreFilm.setText(movie.Title);
        // Et ainsi de suite pour les autres informations
    }

}