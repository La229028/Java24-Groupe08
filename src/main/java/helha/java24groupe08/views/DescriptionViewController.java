package helha.java24groupe08.views;

import helha.java24groupe08.models.MovieDBController;
import helha.java24groupe08.models.MovieDBController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the DescriptionView.
 * This class is responsible for handling user interactions with the DescriptionView,
 * and updating the view based on changes in the model (Movie).
 */
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DescriptionViewController implements Initializable {
    @FXML
    private Label movieLabel;
    @FXML
    private ImageView movieImage;
    @FXML
    private Label moviePlot;
    @FXML
    private Label movieRuntime;
    @FXML
    private Label movieDirector;
    @FXML
    private Label movieReleaseDate;
    @FXML
    private Label movieActors;
    @FXML
    private Button backButtonIndex;
    @FXML
    private StackPane stackPane;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox.setMargin(stackPane, new Insets(15, 0, 0, 0));
    }

    /**
     * Sets the image of the movie in the view.
     *
     * @param imageUrl The URL of the image to set.
     */
    public void setMovieImage(String imageUrl) {
        // Load and set the image
        if (imageUrl != null) {
            try {
                Image image = new Image(imageUrl);
                movieImage.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the details of the movie in the view.
     *
     * @param movieDetails The array containing the movie details to set.
     */
    public void setMovieDetails(String[] movieDetails) {
        movieLabel.setText(movieDetails[0]);
        setMovieImage(movieDetails[13]); // Assuming poster URL is at index 13
        moviePlot.setText(movieDetails[9]); // Assuming plot is at index 9
        movieRuntime.setText("Runtime: " + movieDetails[4]);
        movieDirector.setText("Director: " + movieDetails[6]);
        movieReleaseDate.setText("Release date: " + movieDetails[3]);
        movieActors.setText("Actors: " + movieDetails[8]);
    }

    /**
     * Closes the view when the back button is clicked.
     */
    @FXML
    private void backButtonIndexAction() {
        Stage stage = (Stage) backButtonIndex.getScene().getWindow();
        // Call a method to switch to the index view
        // Example: SceneViewsController.switchToIndex(stage);
    }
}
