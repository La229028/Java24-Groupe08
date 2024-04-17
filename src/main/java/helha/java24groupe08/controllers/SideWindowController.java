package helha.java24groupe08.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.function.Consumer;
/**
 * This class is the controller for the side window.
 * It contains the logic for the side window UI elements.
 */
public class SideWindowController {

    private String[] movieDetails;
    private Consumer<String> deleteMovieCallback;

    /**
     * This method initializes the side window with the movie details and the delete movie callback.
     *
     * @param movieDetails        The details of the movie to be displayed in the side window.
     * @param deleteMovieCallback The callback function to be called when the delete button is clicked.
     */
    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
    }


    /**
     * This method is called when the delete button is clicked.
     * It calls the deleteMovieCallback function with the movie title as the argument.
     * It also closes the side window.
     *
     * @param event The action event triggered by the delete button click.
     */
    @FXML
    private void deleteMovieAction(ActionEvent event) {
        String movieTitle = movieDetails[0];
        if (deleteMovieCallback != null) {
            deleteMovieCallback.accept(movieTitle);
        }
        // Close the side window
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
