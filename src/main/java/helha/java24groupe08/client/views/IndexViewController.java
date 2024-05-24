package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.AlertUtils;
import helha.java24groupe08.client.controllers.AuthentificationController;
import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.Session;
import helha.java24groupe08.client.models.SessionDBController;
import helha.java24groupe08.client.models.UserSession;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

import java.io.IOException;
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
    public Button logoutButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCartButtonVisibility();
        updateBuyButtonVisibility();
        updateButtonVisibility();

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
        logoutButton.setOnAction(event -> handleLogoutButtonAction(event));
        searchButton.setOnAction(event -> onSearch());
        refreshButton.setOnAction(event -> onRefresh());

        genreComboBox.setOnAction(event -> filterAndSortMovies());
        languageComboBox.setOnAction(event -> filterAndSortMovies());
        sortComboBox.setOnAction(event -> filterAndSortMovies());
    }

    public void createVBoxes(List<String[]> movies) {
        for (String[] movieDetails : movies) {
            VBox vbox = createMovieVBox(movieDetails);
            flowPane.getChildren().add(vbox);
        }
    }

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

                poster.setOnMouseClicked(event -> handlePosterClick(movieDetails));
            } catch (IllegalArgumentException e) {
                AlertUtils.showErrorAlert("Image not found: " + movieDetails[13]);
            }
        }
        poster.setOnMouseClicked(event -> handlePosterClick(movieDetails));
        return poster;
    }

    private void handlePosterClick(String[] movieDetails) {
        if (listener != null) {
            if (AuthentificationController.isAdminLoggedIn()) {
                openSideWindow(movieDetails);
            } else {
                listener.seeMoreAction(movieDetails);
            }
        }
    }


    public void openSideWindow(String[] movieDetails) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/sideWindow.fxml"));
            Parent root = loader.load();

            SideWindowViewController controller = loader.getController();
            controller.initData(movieDetails, this::deleteMovie, this::modifiedVBox);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Side Window");
            stage.show();
        } catch (Exception e) {
            AlertUtils.showErrorAlert("Error opening side window: " + e.getMessage());
            e.printStackTrace(); // Consider logging the stack trace for more detailed diagnostics
        }
    }

    public void deleteMovie(String title) {
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
                    .filter(movie -> movie[5] != null && selectedGenre != null && movie[5].contains(selectedGenre))
                    .collect(Collectors.toList());
        }
        if (!"All".equals(selectedLanguage)) {
            searchResults = searchResults.stream()
                    .filter(movie -> movie[10] != null && selectedLanguage.equals(movie[10]))
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

    @FXML
    public void onRefresh() {
        searchField.clear();
        genreComboBox.setValue("All");
        languageComboBox.setValue("All");
        sortComboBox.setValue("Title");

        List<String[]> allMovies = MovieDBController.getAllMovies();
        updateVBoxes(allMovies);

        isSearchPerformed = false;
    }

    private void filterAndSortMovies() {
        String selectedGenre = genreComboBox.getValue();
        String selectedLanguage = languageComboBox.getValue();
        String sort = sortComboBox.getValue();

        List<String[]> movies = MovieDBController.filterAndSortMovies(selectedGenre, selectedLanguage, sort);

        updateVBoxes(movies);
    }

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

    public void modifiedVBox(String[] movieDetails) {
        flowPane.getChildren().clear();

        List<String[]> allMovies = MovieDBController.getAllMovies();
        for (String[] details : allMovies) {
            VBox vbox = createMovieVBox(details);
            flowPane.getChildren().add(vbox);
        }
        scrollPane.setContent(flowPane);
    }

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
            AlertUtils.showErrorAlert("Invalid MovieID format: " + e.getMessage());
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
            AlertUtils.showErrorAlert("An error occurred while loading the cart view : " + e.getMessage());
        }
    }

    public void updateCartButtonVisibility() {
        if (UserSession.getInstance().getUser() == null || AuthentificationController.isAdminLoggedIn()) {
            cartButton.setVisible(false);
        } else {
            cartButton.setVisible(true);
        }
    }

    public void updateBuyButtonVisibility() {
        if (AuthentificationController.isLoggedIn()) {
            cartButton.setVisible(true);
        } else {
            cartButton.setVisible(false);
        }
    }

    public void updateButtonVisibility() {
        if (AuthentificationController.isLoggedIn()) {
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
            if (!AuthentificationController.isAdminLoggedIn()) {
                cartButton.setVisible(true);
            } else {
                cartButton.setVisible(false);
            }
        } else {
            loginButton.setVisible(true);
            logoutButton.setVisible(false);
            cartButton.setVisible(false);
        }
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        AuthentificationController.logout();
        updateButtonVisibility();
        updateCartButtonVisibility();

        AlertUtils.showInfoAlert("Successful disconnection");
    }

    public interface Listener {
        void loginButtonAction();
        void seeMoreAction(String[] movieDetails);
    }
}
