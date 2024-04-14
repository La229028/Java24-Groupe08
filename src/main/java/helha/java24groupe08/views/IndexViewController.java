package helha.java24groupe08.views;

import helha.java24groupe08.controllers.MovieController;
import helha.java24groupe08.models.Movie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the IndexView.
 * This class is responsible for handling user interactions with the IndexView,
 * and updating the view based on changes in the model (Movie).
 */
public class IndexViewController implements Initializable {

    @FXML
    public Label titleLabel;
    @FXML
    public Pane pane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Button loginButton;

    private static Listener listener;

    /**
     * Sets the listener for this controller.
     *
     * @param listener The listener to be set.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("CINEMA");
        List<Movie> movies = MovieController.loadMovieData();
        createVBoxes(movies);
        scrollPane.setContent(pane);

        loginButton.setOnAction(event -> loginButtonAction());
    }


    /**
     * Creates VBox elements for each movie and adds them to the pane.
     *
     * @param movies The list of movies to create VBox elements for.
     */
    private void createVBoxes(List<Movie> movies) {
        int boxWidth = 150;
        int boxHeight = 300;
        int horizontalGap = 25;
        int verticalGap = 30;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                VBox vbox = createVBox(movies.get(i * 6 + j));
                pane.getChildren().add(vbox);
                vbox.setLayoutX(35 + i * (boxWidth + horizontalGap));
                vbox.setLayoutY(30 + j * (boxHeight + verticalGap));
            }
        }
    }

    /**
     * Creates a VBox for a single movie.
     *
     * @param movie The movie to create a VBox for.
     * @return The created VBox.
     */
    private VBox createVBox(Movie movie) {
        VBox vbox = new VBox();
        vbox.setPrefSize(150, 300);
        vbox.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10px; -fx-spacing: 10px;");

        AnchorPane movieTitle = createMovieTitle(movie.getTitle());
        AnchorPane poster = createPoster(movie.getPoster());
        Button seeMoreButton = createSeeMoreButton(movie); // Set film as parameter


        vbox.getChildren().addAll(movieTitle, poster, seeMoreButton);
        vbox.setAlignment(Pos.CENTER); // Center the VBox content
        return vbox;
    }

    /**
     * Creates an AnchorPane for the movie title.
     *
     * @param title The title of the movie.
     * @return The created AnchorPane.
     */
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

    /**
     * Creates an AnchorPane for the poster image.
     *
     * @param posterURL The URL of the poster image.
     * @return The created AnchorPane.
     */
    private AnchorPane createPoster(String posterURL) {
        AnchorPane poster = new AnchorPane();
        poster.setPrefSize(150, 200);
        poster.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");

        // Create delete Button
        Button deleteButton = new Button("X");
        deleteButton.setPrefSize(30, 30);
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 15px;");
        deleteButton.setMinSize(30, 30);
        deleteButton.setMaxSize(30, 30);
        deleteButton.setOnAction(event -> {
            // Delete the movie from the database
            MovieController.deleteMovie(posterURL);
            // Remove the VBox from the pane
            pane.getChildren().remove(poster.getParent()); // Remove the parent of the poster (VBox)
        });
        // position of the delete button
        AnchorPane.setTopAnchor(deleteButton, 0.0);
        AnchorPane.setRightAnchor(deleteButton, 0.0);


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
        poster.getChildren().add(deleteButton);
        return poster;
    }

    /**
     * Creates a "See More" button.
     *
     * @param movie The movie that the button is associated with.
     * @return The created button.
     */
    private static Button createSeeMoreButton(Movie movie) {
        Button seeMoreButton = new Button("See more");
        seeMoreButton.setPrefSize(150, 30);
        seeMoreButton.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white; -fx-font-size: 12pt; -fx-font-weight: bold;");

        // Add an event handler for the click on the button
        seeMoreButton.setOnAction(event -> {
            if (listener != null) {
                listener.seeMoreButtonAction(movie);
            }
        });
        return seeMoreButton;
    }

    /**
     * Open the login page when the login button is clicked.
     */
    private void loginButtonAction() {
        if (listener != null) {
            listener.loginButtonAction();
        }
    }

    /**
     * Interface for the listener of this controller.
     */
    public interface Listener {
        void loginButtonAction();
        void seeMoreButtonAction(Movie movie);
    }

}
