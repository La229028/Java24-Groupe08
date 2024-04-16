package helha.java24groupe08.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Label moviePlot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic here
    }

    public void setMovieDetails(String[] movieDetails) {

        movieLabel.setText(movieDetails[0]); // Assuming title is at index 0
        setMovieImage(movieDetails[13]); // Assuming poster URL is at index 13
        moviePlot.setText(movieDetails[9]); // Assuming plot is at index 9
    }

    public void setMovieImage(String imageUrl) {

        if (imageUrl != null) {
            try {
                Image image = new Image(imageUrl);
                movieImage.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private Button backButton;

    public void handleBackButton(ActionEvent event) {
        /*fermeture de la fenêtre actuelle*/
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}