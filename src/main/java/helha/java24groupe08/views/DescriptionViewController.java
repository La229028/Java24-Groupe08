package helha.java24groupe08.views;

import helha.java24groupe08.models.Movie;
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
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox.setMargin(stackPane, new Insets(15, 0, 0, 0));

//        backButtonIndex.setOnAction(event -> {
//            try {
//                backButtonIndexAction();
//                System.out.println("CLICK !");//test to see if button click works
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
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
     * @param movie The Movie object containing the movie details to set.
     */
   public void setMovieDetails(Movie movie) {
        movieLabel.setText(movie.getTitle());
        movieRuntime.setText(movie.getRuntime() + " | " + movie.getGenre());
        moviePlot.setText("Synopsis\n" + movie.getPlot());
        movieReleaseDate.setText("Sortie\n" + movie.getReleased());
        movieDirector.setText("Réalisateur\n" + movie.getDirector());
        movieActors.setText("Casting\n" + movie.getActors());
    }

    /**
     * Closes the view when the back button is clicked.
     * NE FONCTIONNE PAS
     */
    @FXML
    private void backButtonIndexAction() {
        System.out.println("CLICK !");
        Stage stage = (Stage) backButtonIndex.getScene().getWindow();
        try {
            SceneViewsController.switchToIndex(stage);
        } catch (IOException e) {
            System.out.println("Error switching to index view");
            throw new RuntimeException(e);
        }
    }

}
