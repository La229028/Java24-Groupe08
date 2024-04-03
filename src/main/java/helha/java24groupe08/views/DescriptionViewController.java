package helha.java24groupe08.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DescriptionViewController {
    @FXML
    private Label movieLabel;
    @FXML
    private ImageView movieImage;
    @FXML
    private Label moviePlot;


    public void setMovieTitle(String title) {
        movieLabel.setText(title);
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

    public void setMoviePlot(String plot) {
        moviePlot.setText(plot);
    }

}
