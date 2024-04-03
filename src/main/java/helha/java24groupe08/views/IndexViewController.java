package com.java24groupe08.views;

import com.java24groupe08.controllers.FilmController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.java24groupe08.models.Film;

import java.util.List;

public class IndexViewController {
    @FXML
    public Label titleLabel;

    @FXML
    public Pane pane;

    @FXML
    public ScrollPane scrollPane;

    public void initialize() {
        titleLabel.setText("CinémaXYZ");

        List<Film> films = FilmController.loadFilmData();
        createVBoxes(pane, films);


        scrollPane.setContent(pane);
    }

    private void createVBoxes(Pane pane, List<Film> films){
        for (int i = 0; i < 5; i++) {//column
            for (int j = 0; j < 6; j++) {//row
                VBox vbox = new VBox();
                vbox.setPrefSize(130, 200);
                vbox.setStyle("-fx-background-color: black;");

                AnchorPane movieTitle = new AnchorPane();
                movieTitle.setPrefSize(130, 50);
                movieTitle.setStyle("-fx-background-color: #95d8fc;");

                Label filmTitle = new Label(films.get(i * 6 + j).getTitle());
                filmTitle.setWrapText(true);
                filmTitle.setPrefSize(130, 40);
                filmTitle.setAlignment(Pos.CENTER);
                movieTitle.getChildren().add(filmTitle);

                AnchorPane poster = new AnchorPane();
                poster.setStyle("-fx-background-color: #e1f8fc;");

                String posterURL = films.get(i * 6 + j).getPoster();
                if(posterURL != null){
                    try{
                        Image image = new Image(posterURL);
                        ImageView imageView = new ImageView(image);
                        imageView.setPreserveRatio(true);

                        vbox.setPrefWidth(imageView.getFitWidth());
                        //vbox.setPrefHeight(imageView.getFitHeight());
                        imageView.setFitWidth(130);

                        poster.getChildren().add(imageView);
                    }catch(IllegalArgumentException e){//it runs even if the image is not found
                        System.err.println("Image not found" + posterURL);
                    }
                }


                Button seeMoreButton = new Button("Voir plus");
                seeMoreButton.setPrefSize(130, 30);

                vbox.getChildren().addAll(movieTitle, poster, seeMoreButton);

                pane.getChildren().add(vbox);
                vbox.setLayoutX(35 + i * 180);//distance between each vbox
                vbox.setLayoutY(30 + j * 300);
            }
        }
    }
}