package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.ErrorUtils;
import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.Session;
import helha.java24groupe08.client.models.SessionDBController;
import helha.java24groupe08.client.models.UserSession;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class IndexViewController implements Initializable {
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    public Label titleLabel;
    @FXML
    public FlowPane flowPane;

    @FXML
    private Button cartButton;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Button loginButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;

    // List to store Vbox
    public boolean isSearchPerformed = false;
    private static Listener listener;

    private static IndexViewController instance;

    public void setListener(Listener listener) {
        IndexViewController.listener = listener;
    }

    public IndexViewController() {
        instance = this;
    }
    public static IndexViewController getInstance() {
        return instance;
    }

    /**
     * Initialize the Index view with the main movie list, setting up action handlers
     * and ensuring that the scroll pane and flow pane are properly configured.
     *
     * @param url            URL for resolving relative paths.
     * @param resourceBundle Resources for localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCartButtonVisibility();

        titleLabel.setText("CINEMA");

        genreComboBox.setItems(FXCollections.observableArrayList("All", "Action", "Comedy", "Drama"));
        languageComboBox.setItems(FXCollections.observableArrayList("All", "English", "French", "Spanish"));
        sortComboBox.setItems(FXCollections.observableArrayList("Title", "Release Date"));


        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        flowPane.setPrefWrapLength(800);
        flowPane.setVgap(20);
        flowPane.setHgap(20);


        flowPane.setAlignment(Pos.CENTER);
        flowPane.setStyle("-fx-background-color: white;");


        List<String[]> movies = MovieDBController.getAllMovies();
        createVBoxes(movies);
        scrollPane.setContent(flowPane);

        loginButton.setOnAction(event -> loginButtonAction());
        searchButton.setOnAction(event -> onSearch());
        refreshButton.setOnAction(event -> onRefresh());

        genreComboBox.setOnAction(event -> filterAndSortMovies());
        languageComboBox.setOnAction(event -> filterAndSortMovies());
        sortComboBox.setOnAction(event -> filterAndSortMovies());
    }


    /**
     * This method creates the VBox elements for each movie and adds them to the FlowPane.
     *
     * @param movies The list of movies to be displayed.
     */
    public void createVBoxes(List<String[]> movies) {
        for (String[] movieDetails : movies) {
            VBox vbox = createMovieVBox(movieDetails);
            flowPane.getChildren().add(vbox);
        }
    }


    /**
     * This method creates a VBox element for a movie with the given details.
     *
     * @param movieDetails The details of the movie to be displayed.
     * @return The VBox element containing the movie details.
     */
    private VBox createMovieVBox(String[] movieDetails) {
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
                        listener.seeMoreAction(movieDetails);
                    }
                });
            } catch (IllegalArgumentException e) {
                ErrorUtils.showErrorAlert("Image not found: " + movieDetails[13]);
            }
        }
        poster.setOnMouseClicked(event -> {
            if (listener != null) {
                if (LoginViewController.isAdminLoggedIn()) {
                    openSideWindow(movieDetails);
                } else {
                    listener.seeMoreAction(movieDetails);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/sideWindow.fxml"));
            Parent root = loader.load();

            SideWindowViewController controller = loader.getController();
            if (controller == null) {
                throw new RuntimeException("Controller not found. Check FXML controller assignment.");
            }

            controller.initData(movieDetails, this::deleteMovie, this::modifiedVBox);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Side Window");
            stage.show();
        } catch (Exception e) {  // Catch more broadly to diagnose issues
            ErrorUtils.showErrorAlert("Error opening side window: " + e.getMessage());
            e.printStackTrace();  // Consider logging the stack trace for more detailed diagnostics
        }
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
     * Method called when a search is performed.
     * It searches for movies matching the text entered in the search field,
     * filtering according to the selected genre and language, and then sorting based on the chosen criteria.
     */
    @FXML
    private void onSearch() {

        String searchText = searchField.getText();
        if (searchText == null || searchText.trim().isEmpty()) {
            return;
        }

        List<String[]> searchResults = MovieDBController.searchMoviesByTitle(searchText);

        String selectedGenre = genreComboBox.getValue();
        String selectedLanguage = languageComboBox.getValue();
        if (!"All".equals(selectedGenre)) {
            searchResults = searchResults.stream()
                    .filter(movie -> movie[5] != null && movie[5].contains(selectedGenre))
                    .collect(Collectors.toList());
        }
        if (!"All".equals(selectedLanguage)) {
            searchResults = searchResults.stream()
                    .filter(movie -> movie[10] != null && movie[10].equals(selectedLanguage))
                    .collect(Collectors.toList());
        }

        String sort = sortComboBox.getValue();
        if ("Title".equals(sort)) {
            searchResults.sort((a, b) -> a[0].compareToIgnoreCase(b[0]));
        } else if ("Release Date".equals(sort)) {
            searchResults.sort((a, b) -> a[3].compareToIgnoreCase(b[3]));
        }

        updateVBoxes(searchResults);
        searchField.clear();
        isSearchPerformed = true;
    }


    /**
     * Method called when a refresh is requested.
     * It resets the search field and ComboBoxes to default values,
     * then retrieves and displays all available movies.
     */
    @FXML
    public void onRefresh() {
        searchField.clear();
        // Variables for monitoring the initial state of ComboBoxes
        String initialGenre = "All";
        genreComboBox.setValue(initialGenre);
        String initialLanguage = "All";
        languageComboBox.setValue(initialLanguage);
        String initialSort = "Title";
        sortComboBox.setValue(initialSort);

        List<String[]> allMovies = MovieDBController.getAllMovies();
        updateVBoxes(allMovies);

        isSearchPerformed = false;
    }

    /**
     * Filters and sorts the movies based on the selected genre, language, and sort criteria.
     */
    private void filterAndSortMovies() {
        String selectedGenre = genreComboBox.getValue();
        String selectedLanguage = languageComboBox.getValue();
        String sort = sortComboBox.getValue();

        List<String[]> movies = MovieDBController.filterAndSortMovies(selectedGenre, selectedLanguage, sort);

        updateVBoxes(movies);
    }

    /**
     * Updates the VBoxes in the FlowPane with the details of the provided movies.
     * If no movies are found, displays a message indicating that no movies were found.
     *
     * @param movies The list of movies to be displayed.
     */
    private void updateVBoxes(List<String[]> movies) {
        flowPane.getChildren().clear();
        if (movies.isEmpty()) {
            flowPane.getChildren().add(createNoResultsVBox());
        } else {
            for (String[] movieDetails : movies) {
                VBox vbox = createMovieVBox(movieDetails);
                flowPane.getChildren().add(vbox);
            }
        }
        scrollPane.setContent(flowPane);
    }

    /**
     * Updates the VBox in the FlowPane with the details of the provided movie.
     * Clears the existing VBox, creates a new VBox with the details of the specified movie,
     * and sets it as the content of the FlowPane.
     *
     * @param movieDetails The details of the movie to be displayed in the updated VBox.
     */
    private void modifiedVBox(String[] movieDetails) {
        flowPane.getChildren().clear();

        List<String[]> allMovies = MovieDBController.getAllMovies();
        for(String[] details : allMovies){
            VBox vbox = createMovieVBox(details);
            flowPane.getChildren().add(vbox);
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
        return vbox;
    }

    @FXML
    private TableView<Session> sessionTable;

    public void initSessionTable(int movieId) {
        try {
            List<Session> sessions = SessionDBController.getSessionsByMovieId(movieId);
            sessionTable.setItems(FXCollections.observableArrayList(sessions));
        } catch (Exception e) {
            ErrorUtils.showErrorAlert("Invalid MovieID format: " + e.getMessage());
        }
    }

    @FXML
    private void handleCartButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/cartView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Cart");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorUtils.showErrorAlert("An error occurred while loading the cart view : " + e.getMessage());
        }
    }

    public void updateCartButtonVisibility() {
        if(UserSession.getInstance().getUser() == null) {
            cartButton.setVisible(false);
        } else {
            cartButton.setVisible(true);
        }
    }

    /**
     * This interface defines the methods that the listener of the index view must implement.
     */
    public interface Listener {
        void loginButtonAction();
        void seeMoreAction(String[] movieDetails);
        //void seeMoreButtonAction(String movieTitle);
    }
}
