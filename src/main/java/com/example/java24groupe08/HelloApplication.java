package com.example.java24groupe08;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("index.fxml"));
        AnchorPane indexPane = fxmlLoader.load();

        BorderPane root = new BorderPane();//placement for cinema title

        root.setCenter(indexPane);

        Scene scene = new Scene(root, 850, 500);

        stage.setTitle("Cinéma");
        stage.setScene(scene);
        stage.show();

        Pane pane = new Pane();

        for (int i = 0; i < 4; i++) {//column
            for (int j = 0; j < 2; j++) {//row
                VBox vbox = new VBox();
                vbox.setPrefSize(105, 139);
                vbox.setStyle("-fx-background-color: blue;");

                AnchorPane movieTitle = new AnchorPane();
                movieTitle.setPrefSize(90, 30);
                movieTitle.setStyle("-fx-background-color: #6fe7fc;");

                AnchorPane poster = new AnchorPane();
                poster.setPrefSize(106, 116);
                poster.setStyle("-fx-background-color: #e1f8fc;");

                Button buyButton = new Button("Acheter");
                buyButton.setPrefSize(106, 21);

                vbox.getChildren().addAll(movieTitle, poster, buyButton);

                pane.getChildren().add(vbox);
                scene.setRoot(pane);
                vbox.setLayoutX(170 + i * 133);//distance between each vbox
                vbox.setLayoutY(81 + j * 150);
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}