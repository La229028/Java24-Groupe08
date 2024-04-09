package helha.java24groupe08.views;

import helha.java24groupe08.controllers.MovieController;
import helha.java24groupe08.models.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class IndexViewController {
    @FXML
    public Label titleLabel;

    @FXML
    public Pane pane;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public Button loginButton;

    // Initialize method to set the title label and load film data
    public void initialize() {
        titleLabel.setText("CINEMA");
        List<Movie> movies = MovieController.loadFilmData();
        createVBoxes(movies);
        scrollPane.setContent(pane);

        loginButton.setOnAction(event -> loginButtonAction());
    }

    // Create VBox elements for each film and add them to the pane
    private void createVBoxes(List<Movie> movies) {
        int boxWidth = 150;
        int boxHeight = 300;
        int horizontalGap = 20;
        int verticalGap = 25;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                VBox vbox = createVBox(movies.get(i * 6 + j));
                pane.getChildren().add(vbox);
                vbox.setLayoutX(35 + i * (boxWidth + horizontalGap));
                vbox.setLayoutY(30 + j * (boxHeight + verticalGap));
            }
        }
    }

    // Create a VBox for a single film
    private VBox createVBox(Movie movie) {
        VBox vbox = new VBox();
        vbox.setPrefSize(150, 300);
        vbox.setStyle("-fx-background-color: #F0F0F0; -fx-padding: 10px; -fx-spacing: 10px; -fx-border-color: black; -fx-border-width: 1px;");

        AnchorPane movieTitle = createMovieTitle(movie.getTitle());
        AnchorPane poster = createPoster(movie.getPoster());
        Button seeMoreButton = createSeeMoreButton(movie); // Passer le film en tant que paramètre


        vbox.getChildren().addAll(movieTitle, poster, seeMoreButton);
        vbox.setAlignment(Pos.CENTER); // Center the VBox content
        return vbox;
    }

    // Create an AnchorPane for the movie title
    private AnchorPane createMovieTitle(String title) {
        AnchorPane movieTitle = new AnchorPane();
        movieTitle.setPrefSize(150, 50);
        movieTitle.setStyle("-fx-background-color: #6495ED; -fx-padding: 5px;");

        Label filmTitle = new Label(title);
        filmTitle.setWrapText(true);
        filmTitle.setPrefSize(140, 40);
        filmTitle.setAlignment(Pos.CENTER);
        filmTitle.setStyle("-fx-font-size: 14pt; -fx-text-fill: white;");
        AnchorPane.setLeftAnchor(filmTitle, 5.0);
        AnchorPane.setRightAnchor(filmTitle, 5.0);
        AnchorPane.setTopAnchor(filmTitle, 5.0);
        AnchorPane.setBottomAnchor(filmTitle, 5.0);
        movieTitle.getChildren().add(filmTitle);

        return movieTitle;
    }

    // Create an AnchorPane for the poster image
    private AnchorPane createPoster(String posterURL) {
        AnchorPane poster = new AnchorPane();
        poster.setPrefSize(150, 200);
        poster.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");

        if (posterURL != null) {
            try {
                Image image = new Image(posterURL);
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(130);
                imageView.setFitHeight(180);
                AnchorPane.setLeftAnchor(imageView, 10.0);
                AnchorPane.setRightAnchor(imageView, 10.0);
                AnchorPane.setTopAnchor(imageView, 10.0);
                AnchorPane.setBottomAnchor(imageView, 10.0);
                poster.getChildren().add(imageView);
            } catch (IllegalArgumentException e) {
                System.err.println("Image not found" + posterURL);
            }
        }
        return poster;
    }

    private Button createSeeMoreButton(Movie movie) {
        Button seeMoreButton = new Button("Voir plus");
        seeMoreButton.setPrefSize(150, 30);
        seeMoreButton.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white; -fx-font-size: 12pt; -fx-font-weight: bold;");

        // Add an event handler for the click on the button
        seeMoreButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/descrip.fxml"));
                Parent root = loader.load();

                DescriptionViewController descriptionViewController = loader.getController();
                descriptionViewController.setMovieImage(movie.getPoster());
                descriptionViewController.setMovieDetails(movie);//retrieve film details

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return seeMoreButton;
    }

    // Open the login page
    private void loginButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.setResizable(false);//page cannot be resized
            stage.initModality(Modality.APPLICATION_MODAL);//The page cannot be closed without logging in
            stage.initOwner(loginButton.getScene().getWindow());//The main page cannot be clicked
            stage.showAndWait();//The main page cannot be clicked until the login page is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
