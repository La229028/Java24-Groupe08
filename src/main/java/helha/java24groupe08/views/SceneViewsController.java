package helha.java24groupe08.views;

import helha.java24groupe08.models.MovieDBController;
import helha.java24groupe08.models.MovieDBController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneViewsController {

    public static void switchToDescription(Stage currentStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneViewsController.class.getResource("descrip.fxml"));
        Scene scene = new Scene(loader.load());
        DescriptionViewController controller = loader.getController();
        // You may need to pass necessary parameters to the controller here
        Stage stage = currentStage;
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToIndex(Stage currentStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneViewsController.class.getResource("index.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = currentStage;
        stage.setScene(scene);
        stage.show();
    }
}

