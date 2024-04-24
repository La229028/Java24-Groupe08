package helha.java24groupe08.views;

import helha.java24groupe08.controllers.SideWindowController;
import helha.java24groupe08.models.MovieDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    public Pane searchMovie;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;

    // List to store Vbox
    private boolean isSearchPerformed = false;
    private static Listener listener;

    public void setListener(Listener listener) {
        IndexViewController.listener = listener;
    }

    /**
     * This method is called when the index view is initialized.
     * It sets up the initial state of the index view.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("CINEMA");
        List<String[]> movies = MovieDBController.getAllMovies();
        createVBoxes(movies);
        scrollPane.setContent(flowPane);

        loginButton.setOnAction(event -> loginButtonAction());
        searchButton.setOnAction(event -> onSearch());
        refreshButton.setOnAction(event -> onRefresh());
    }


    /**
     * This method creates the VBox elements for each movie and adds them to the FlowPane.
     *
     * @param movies The list of movies to be displayed.
     */
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



    /**
     * This method creates a VBox element for a movie with the given details.
     *
     * @param movieDetails The details of the movie to be displayed.
     * @return The VBox element containing the movie details.
     */
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


    /**
     * This method creates an AnchorPane element for the movie title.
     *
     * @param title The title of the movie.
     * @return The AnchorPane element containing the movie title.
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
     * This method creates an AnchorPane element for the movie poster.
     *
     * @param movieDetails The details of the movie.
     * @return The AnchorPane element containing the movie poster.
     */
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred");
                alert.setContentText("Image not found: " + movieDetails[13]);
                alert.showAndWait();
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



    /**
     * This method opens the side window for the admin to delete a movie.
     *
     * @param movieDetails The details of the movie to be deleted.
     */
    private void openSideWindow(String[] movieDetails) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sideWindow.fxml"));
            Parent root = loader.load();

            SideWindowController controller = loader.getController();
            controller.initData(movieDetails, this::deleteMovie, this::updateVBox);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Side Window");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText("Error opening side window: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateVBox(String[] updatedMovieDetails) {
        // Clear the FlowPane
        flowPane.getChildren().clear();

        // Get all movies from the database
        List<String[]> movies = MovieDBController.getAllMovies();

        // Create VBoxes for each movie
        createVBoxes(movies);

        // Set the FlowPane as the content of the ScrollPane
        scrollPane.setContent(flowPane);
    }


    /**
     * This method deletes the movie with the given title.
     *
     * @param title The title of the movie to be deleted.
     */
    private void deleteMovie(String title) {
        MovieDBController.deleteMovie(title);
        flowPane.getChildren().clear();
        List<String[]> movies = MovieDBController.getAllMovies();
        createVBoxes(movies);
        scrollPane.setContent(flowPane);
    }


    /**
     * This method is called when the login button is clicked.
     * It calls the loginButtonAction method of the listener.
     */
    private void loginButtonAction() {
        if (listener != null) {
            listener.loginButtonAction();
        }
    }
    /**
     * Handles the search action when the search button is clicked or a search query is submitted.
     * It retrieves the movies that match the search query and updates the display with the results.
     * If no movies are found, it displays a message indicating that no results were found.
     */
    @FXML
    private void onSearch() {
        String searchText = searchField.getText();
        if (searchText == null || searchText.trim().isEmpty()) {
            // Si la barre de recherche est vide ou ne contient que des espaces blancs, ne rien faire.
            return;
        }

        // Procédez avec la recherche car l'utilisateur a saisi du texte.
        List<String[]> searchResults = MovieDBController.searchMoviesByTitle(searchText);
        updateVBoxes(searchResults); // Votre logique pour afficher les résultats

        searchField.clear();
        isSearchPerformed = true;
    }
    /**
     * Handles the refresh action when the refresh button is clicked.
     * It resets the movie display to the initial full list of movies.
     */
    @FXML
    private void onRefresh() {
        if (isSearchPerformed) {
            List<String[]> allMovies = MovieDBController.getAllMovies();
            updateVBoxes(allMovies);
            isSearchPerformed = false;
        }
        searchField.clear(); // Efface le texte de la barre de recherche indépendamment de si isSearchPerformed est vrai
    }
    /**
     * Updates the display with a given list of movies. Each movie is represented by a VBox.
     * If the list is empty, a placeholder VBox is displayed with a message indicating no movies were found.
     *
     * @param movies The list of movies to display.
     */
    private void updateVBoxes(List<String[]> movies) {
        flowPane.getChildren().clear();
        if (movies.isEmpty()) {
            flowPane.getChildren().add(createNoResultsVBox());
        } else {
            for (String[] movieDetails : movies) {
                VBox vbox = createVBox(movieDetails);
                flowPane.getChildren().add(vbox);
            }
        }
        scrollPane.setContent(flowPane);
    }
    /**
     * Creates and returns a VBox that displays a message indicating no movies were found.
     *
     * @return A VBox with a "Not Found" message.
     */
    private VBox createNoResultsVBox() {
        VBox vbox = new VBox();
        vbox.setPrefSize(150, 300);
        vbox.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10px; -fx-spacing: 10px;");

        Label label = new Label("No movies found");
        label.setFont(new Font("Arial", 16));
        label.setAlignment(Pos.CENTER);
        vbox.getChildren().add(label);
        vbox.setAlignment(Pos.CENTER);
        return vbox;}

    /**
     * This interface defines the methods that the listener of the index view must implement.
     */
    public interface Listener {
        void loginButtonAction();
        void seeMoreButtonAction(String[] movieDetails);
        //void seeMoreButtonAction(String movieTitle);
    }
}
