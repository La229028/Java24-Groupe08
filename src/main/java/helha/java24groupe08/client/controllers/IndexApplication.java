/**
 * The IndexApplication class represents the main application for the cinema index.
 * It handles the initialization of the main stage and the actions related to user interactions.
 */
package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.exceptions.MovieNotFoundException;
import helha.java24groupe08.client.views.DescriptionViewController;
import helha.java24groupe08.client.views.IndexViewController;

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
    private final MovieDBController movieDBController = new MovieDBController();
    //private MovieAPIController movieAPIController = new MovieAPIController();


    /**
     * The main method of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(new String[0]);
    }


    /**
     * Default constructor for IndexApplication.
     */
    public IndexApplication() {
    }


    /**
     * Initializes the main stage of the application.
     *
     * @param stage The primary stage of the application.
     */
    public void start(Stage stage) {
        try {
            this.indexStage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/helha/java24groupe08/views/index.fxml"));
            Scene scene = new Scene((Parent)fxmlLoader.load());
            IndexViewController controller = (IndexViewController)fxmlLoader.getController();
            controller.setListener(this);
            stage.setTitle("Cinéma");
            stage.setScene(scene);
            stage.show();
        } catch (javafx.fxml.LoadException e){
            showErrorAlert("Error while loading the main view: " + e.getMessage());
            stage.close();
        } catch (IOException e) {
            showErrorAlert("Other IO error occurred: " + e.getMessage());
            stage.close();
        }
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
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error while loading the login view: " + e.getMessage());
        }
    }

    /**
     * Action performed when the "See More" button is clicked.
     *
     * @param movieTitle The title of the movie for which more information is requested.
     */
    public void seeMoreButtonAction(String movieTitle) {
        String[] movieDetails = null;
        try {
            movieDetails = this.movieDBController.getMovie(movieTitle);
        } catch (MovieNotFoundException e) {
            showErrorAlert("Movie not found: " + e.getMessage());
        } catch (Exception e){
            showErrorAlert("An error occurred: " + e.getMessage());
        } finally{
            if (movieDetails == null) {
                this.showErrorAlert("Movie not found in database: " + movieTitle);
            }
        }
    }

    /**
     * Action performed when the "See More" button is clicked for multiple movies.
     *
     * @param movieTitle Array of movie titles for which more information is requested.
     */
    public void seeMoreButtonAction(String[] movieTitle) {
        String[] movieDetails = null;
        Stage stage = null;
        DescriptionViewController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/helha/java24groupe08/views/descrip.fxml"));
            Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Description");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.indexStage.getOwner());
            controller = (DescriptionViewController)loader.getController();
            movieDetails = this.movieDBController.getMovie(movieTitle[0]);

        } catch (IOException e) {
            this.showErrorAlert("Error while trying to open the description page : " + e.getMessage());
        } catch (MovieNotFoundException e) {
            showErrorAlert("The movie \"" + movieTitle[0] + "\" was not found in the database.");
        }

        if (movieDetails == null) {
            this.showErrorAlert("The movie \"" + movieTitle[0] + "\" was not found in the database.");
            return;
        }

        controller.setMovieDetails(movieDetails);
        stage.showAndWait();
    }





    /**
     * Loads and inserts movies from the Movie API into the database.
     */
    private void loadAndInsertMoviesFromAPI() {
        try {
            String[] titles = new String[]{"title1", "title2", "title3"};
            MovieAPIController.getAndStoreMoviesFromApi(titles);
        } catch (Exception e) {
            this.showErrorAlert("An error occurred while loading and inserting movies from API: " + e.getMessage());
        }

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
}
