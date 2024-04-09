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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox.setMargin(stackPane, new Insets(15, 0, 0, 0));

        backButtonIndex.setOnAction(event -> {
            try {
                backButtonIndexAction();
                System.out.println("CLICK !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


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

   public void setMovieDetails(Movie movie) {
        movieLabel.setText(movie.getTitle());
        movieRuntime.setText(movie.getRuntime() + " | " + movie.getGenre());
        moviePlot.setText("Synopsis\n" + movie.getPlot());
        movieReleaseDate.setText("Sortie\n" + movie.getReleased());
        movieDirector.setText("Réalisateur\n" + movie.getDirector());
        movieActors.setText("Casting\n" + movie.getActors());
    }

    //NE FONCTIONNE PAS
    private void backButtonIndexAction() {
        Stage stage = (Stage) backButtonIndex.getScene().getWindow();
        stage.close();
    }



//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        Stage stage = (Stage) backButtonIndex.getScene().getWindow();
//        stage.close();
//    }


}
