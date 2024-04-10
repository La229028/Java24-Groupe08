package helha.java24groupe08.views;

import helha.java24groupe08.models.Movie;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class SceneViewsController {
    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;
    private Parent root;
    private Movie movie;

    public static void switchToDescription(Movie movie, Stage currentStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(DescriptionViewController.class.getResource("descrip.fxml"));
        Scene scene = new Scene(loader.load());
        DescriptionViewController controller = loader.getController();
        controller.setMovieImage(movie.getPoster());
        controller.setMovieDetails(movie);
        Stage stage = currentStage;
        stage.setScene(scene);
        stage.show();
    }

    public void switchToIndex(ActionEvent event) throws IOException {
        loader = new FXMLLoader(IndexViewController.class.getResource("index.fxml"));
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }


}
