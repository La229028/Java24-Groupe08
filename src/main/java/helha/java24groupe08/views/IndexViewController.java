package helha.java24groupe08.views;

import helha.java24groupe08.controllers.SideWindowController;
import helha.java24groupe08.models.MovieDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class IndexViewController implements Initializable {

    @FXML
    public Label titleLabel;
    @FXML
    public FlowPane flowPane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Button loginButton;

    private static Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("CINEMA");
        List<String[]> movies = MovieDBController.getAllMovies();
        createVBoxes(movies);
        scrollPane.setContent(flowPane);

        loginButton.setOnAction(event -> loginButtonAction());
    }

    private void createVBoxes(List<String[]> movies) {
        int boxWidth = 150;
        int boxHeight = 300;
        int horizontalGap = 25;
        int verticalGap = 30;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                VBox vbox = createVBox(movies.get(i * 6 + j));
                flowPane.getChildren().add(vbox);
                vbox.setLayoutX(35 + i * (boxWidth + horizontalGap));
                vbox.setLayoutY(30 + j * (boxHeight + verticalGap));
            }
        }
    }

    private VBox createVBox(String[] movieDetails) {
        VBox vbox = new VBox();
        vbox.setPrefSize(150, 300);
        vbox.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10px; -fx-spacing: 10px;");

        AnchorPane movieTitle = createMovieTitle(movieDetails[0]);
        AnchorPane poster = createPoster(movieDetails);

        vbox.getChildren().addAll(movieTitle, poster);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

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

    private AnchorPane createPoster(String[] movieDetails) {
        AnchorPane poster = new AnchorPane();
        poster.setPrefSize(150, 200);
        poster.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");

        if (movieDetails[13] != null && !movieDetails[13].isEmpty()) {
            try {
                Image image = new Image(movieDetails[13]);
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(130);
                imageView.setFitHeight(180);
                AnchorPane.setLeftAnchor(imageView, 10.0);
                AnchorPane.setRightAnchor(imageView, 10.0);
                AnchorPane.setTopAnchor(imageView, 10.0);
                AnchorPane.setBottomAnchor(imageView, 10.0);
                poster.getChildren().add(imageView);

                poster.setOnMouseClicked(event -> {
                    if (listener != null) {
                        listener.seeMoreButtonAction(movieDetails);
                    }
                });
            } catch (IllegalArgumentException e) {
                System.err.println("Image not found" + movieDetails[13]);
            }
        }
        poster.setOnMouseClicked(event -> {
            if (listener != null) {
                if (LoginViewController.isAdminLoggedIn()) {
                    openSideWindow(movieDetails);
                } else {
                    listener.seeMoreButtonAction(movieDetails);
                }
            }
        });
        return poster;
    }


    private void openSideWindow(String[] movieDetails) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sideWindow.fxml"));
            Parent root = loader.load();

            SideWindowController controller = loader.getController();
            controller.initData(movieDetails, this::deleteMovie);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Side Window");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteMovie(String title) {
        MovieDBController.deleteMovie(title);
        flowPane.getChildren().clear();
        List<String[]> movies = MovieDBController.getAllMovies();
        createVBoxes(movies);
        scrollPane.setContent(flowPane);
    }

    private void loginButtonAction() {
        if (listener != null) {
            listener.loginButtonAction();
        }
    }

    public interface Listener {
        void loginButtonAction();
        void seeMoreButtonAction(String[] movieDetails);
        void seeMoreButtonAction(String movieTitle);
    }
}
