package helha.java24groupe08.client.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DescriptionViewController implements Initializable {
    @FXML
    private Label movieLabel;
    @FXML
    private ImageView movieImage;
    @FXML
    private Label moviePlotTextArea;

    private String[] movieDetails;

    /**
     * This method is called when the description view is initialized.
     * It sets up the initial state of the description view.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic here
    }

    /**
     * This method sets the details of the movie to be displayed in the description view.
     *
     * @param movieDetails The details of the movie to be displayed.
     */
    public void setMovieDetails(String[] movieDetails) {
        this.movieDetails = movieDetails; // Add this line

        movieLabel.setText(movieDetails[0]); // Assuming title is at index 0
        setMovieImage(movieDetails[13]); // Assuming poster URL is at index 13
        moviePlotTextArea.setText(movieDetails[9]); // Assuming plot is at index 9
    }


    /**
     * This method sets the image of the movie.
     *
     * @param imageUrl The URL of the image to be displayed.
     */
    public void setMovieImage(String imageUrl) {
        if (imageUrl != null) {
            try {
                Image image = new Image(imageUrl);
                movieImage.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error with image recovery");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private Button backButton;

    /**
     * This method is called when the back button is clicked.
     * It closes the current window.
     *
     * @param event The action event triggered by the back button click.
     */
    public void handleBackButton(ActionEvent event) {
        /*fermeture de la fenêtre actuelle*/
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBuyButton(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/buyTicket.fxml"));
            Parent root = loader.load();

            BuyTicketViewController controller = loader.getController();
            controller.setMovieDetails(movieDetails);

            // Load sessions for the movie
            int movieId = Integer.parseInt(movieDetails[14]); // Assuming movie ID is at index 14
            controller.loadSessions(movieId);

            //open the buyTicket page in the description page
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}