package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.views.IndexViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.function.Consumer;
/**
 * This class is the controller for the side window.
 * It contains the logic for the side window UI elements.
 */
public class SideWindowController {

    private String[] movieDetails;
    private Consumer<String> deleteMovieCallback;
    private Consumer<String[]> updateMovieCallback;
    private IndexViewController indexViewController;

    // Reference to fxml fields
    @FXML
    private TextField titleField;

    @FXML
    private TextArea plotArea;


    /**
     * This method initializes the side window with the movie details and the delete movie callback.
     *
     * @param movieDetails        The details of the movie to be displayed in the side window.
     * @param deleteMovieCallback The callback function to be called when the delete button is clicked.
     */
    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback, Consumer<String[]> updateMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
        this.updateMovieCallback = updateMovieCallback;

        // Set the movie title and plot in the text fields
        titleField.setText(movieDetails[0]); // title is at index 0
        plotArea.setText(movieDetails[9]); // plot is at index 9
    }
    /**
     * This method sets the index view controller.
     *
     * @param indexViewController The index view controller.
     */
    public void setIndexViewController(IndexViewController indexViewController) {
        this.indexViewController = indexViewController;
    }

    @FXML
    private void saveChangesOnAction(ActionEvent event) {
        try{
            // Get the new title and plot from the text fields
            String oldTitle = movieDetails[0];
            String newTitle = titleField.getText();
            String newPlot = plotArea.getText();

            // Update the movie details
            movieDetails[0] = newTitle;
            movieDetails[9] = newPlot;

            // Update the movie in the database
            MovieDBController.updateMovieDetails(oldTitle, movieDetails);

            // Call the update callback
            if (updateMovieCallback != null) {
                updateMovieCallback.accept(movieDetails);
            }

            // Close the side window
            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            showErrorAlert("Error updating movie details: " + e.getMessage());
        }
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
        try{
            String movieTitle = movieDetails[0];
            if (deleteMovieCallback != null) {
                deleteMovieCallback.accept(movieTitle);
            }
            // Close the side window
            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (NullPointerException e) {
            showErrorAlert("The delete callback function is not set : " + e.getMessage());
        }
    }

    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
