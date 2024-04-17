/**
 * The IndexApplication class represents the main application for the cinema index.
 * It handles the initialization of the main stage and the actions related to user interactions.
 */
package helha.java24groupe08.controllers;

import helha.java24groupe08.models.MovieDBController;
import helha.java24groupe08.controllers.MovieAPIController;
import helha.java24groupe08.views.DescriptionViewController;
import helha.java24groupe08.views.IndexViewController;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IndexApplication extends Application implements IndexViewController.Listener {
    private Stage indexStage;
    private static final String ERROR_TITLE = "Error";
    private static final String ERROR_HEADER = "An error occurred";
    private MovieDBController movieDBController = new MovieDBController();
    private MovieAPIController movieAPIController = new MovieAPIController();

    /**
     * Default constructor for IndexApplication.
     */
    public IndexApplication() {
    }

    /**
     * Initializes the main stage of the application.
     *
     * @param stage The primary stage of the application.
     * @throws Exception If an error occurs during initialization.
     */
    public void start(Stage stage) throws Exception {
        this.indexStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/helha/java24groupe08/views/index.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load());
        IndexViewController controller = (IndexViewController)fxmlLoader.getController();
        controller.setListener(this);
        stage.setTitle("Cinéma");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Action performed when the login button is clicked.
     */
    public void loginButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/helha/java24groupe08/views/login.fxml"));
            Parent root = (Parent)loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    /**
     * Action performed when the "See More" button is clicked.
     *
     * @param movieTitle The title of the movie for which more information is requested.
     */
    public void seeMoreButtonAction(String movieTitle) {
        try {
            String[] movieDetails = this.movieDBController.getMovie(movieTitle);
            if (movieDetails == null) {
                this.showErrorAlert("Movie not found in database: " + movieTitle);
            }
        } catch (Exception var3) {
            this.showErrorAlert("Error for movie : " + movieTitle + " \n " + var3.getMessage());
        }

    }

    /**
     * Action performed when the "See More" button is clicked for multiple movies.
     *
     * @param movieTitle Array of movie titles for which more information is requested.
     */
    public void seeMoreButtonAction(String[] movieTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/helha/java24groupe08/views/descrip.fxml"));
            Parent root = (Parent)loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Description");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.indexStage.getOwner());
            DescriptionViewController controller = (DescriptionViewController)loader.getController();
            String[] movieDetails = this.movieDBController.getMovie(movieTitle[0]);
            if (movieDetails == null) {
                this.showErrorAlert("The movie \"" + movieTitle[0] + "\" was not found in the database.");
                return;
            }

            controller.setMovieDetails(movieDetails);
            stage.showAndWait();
        } catch (IOException var7) {
            this.showErrorAlert("Error while trying to open the description page : " + var7.getMessage());
        }

    }

    /**
     * The main method of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(new String[0]);
    }

    /**
     * Displays an error alert dialog with the specified content text.
     *
     * @param contentText The content text of the error alert.
     */
    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Loads and inserts movies from the Movie API into the database.
     */
    private void loadAndInsertMoviesFromAPI() {
        try {
            String[] titles = new String[]{"title1", "title2", "title3"};
            MovieAPIController.getAndStoreMoviesFromApi(titles);
        } catch (Exception var2) {
            this.showErrorAlert("An error occurred while loading and inserting movies from API: " + var2.getMessage());
        }

    }
}
