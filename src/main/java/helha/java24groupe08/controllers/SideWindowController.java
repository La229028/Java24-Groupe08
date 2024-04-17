package helha.java24groupe08.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.function.Consumer;

public class SideWindowController {

    private String[] movieDetails;
    private Consumer<String> deleteMovieCallback;

    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
    }

    @FXML
    private void deleteMovieAction(ActionEvent event) {
        String movieTitle = movieDetails[0]; // Assuming the movie title is at index 0
        if (deleteMovieCallback != null) {
            deleteMovieCallback.accept(movieTitle);
        }
        // Close the side window
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
