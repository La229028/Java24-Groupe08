package com.example.java24groupe08.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class IndexViewController {
    @FXML
    public Label titleLabel;

    @FXML
    public Pane pane;

    public void initialize() {
        titleLabel.setText("Cinéma");
        createVBoxes(pane);
    }

    private void createVBoxes(Pane pane){
        for (int i = 0; i < 4; i++) {//column
            for (int j = 0; j < 2; j++) {//row
                VBox vbox = new VBox();
                vbox.setPrefSize(105, 139);
                vbox.setStyle("-fx-background-color: blue;");

                AnchorPane movieTitle = new AnchorPane();
                movieTitle.setPrefSize(90, 40);
                movieTitle.setStyle("-fx-background-color: #6fe7fc;");

                AnchorPane poster = new AnchorPane();
                poster.setPrefSize(106, 116);
                poster.setStyle("-fx-background-color: #e1f8fc;");

                Button buyButton = new Button("Voir plus");//changer le nom
                buyButton.setPrefSize(106, 21);

                vbox.getChildren().addAll(movieTitle, poster, buyButton);

                pane.getChildren().add(vbox);
                vbox.setLayoutX(170 + i * 133);//distance between each vbox
                vbox.setLayoutY(81 + j * 150);
            }
        }
    }
}