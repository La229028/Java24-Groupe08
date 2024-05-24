/**
 * The IndexApplication class represents the main application for the cinema index.
 * It handles the initialization of the main stage and the actions related to user interactions.
 */
package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.views.IndexViewController;
import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.exceptions.MovieNotFoundException;
import helha.java24groupe08.client.views.DescriptionViewController;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The IndexController class represents the main application for the cinema index.
 * It handles the initialization of the main stage and the actions related to user interactions.
 */
public class IndexController extends Application implements IndexViewController.Listener {
    private Stage indexStage;
    private final MovieDBController movieDBController = new MovieDBController();

    /**
     * The main method of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Ensure DatabaseConnection instance is created which will initialize the database
        DatabaseConnection.getInstance();

        System.out.println("Database initialized successfully.");
        launch(new String[0]);  // Assuming this is part of your JavaFX application startup
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
            AlertUtils.showErrorAlert("Error while loading the main view: " + e.getMessage());
            stage.close();
        } catch (IOException e) {
            AlertUtils.showErrorAlert("Other IO error occurred: " + e.getMessage());
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
            AlertUtils.showErrorAlert("Error while loading the login view: " + e.getMessage());
        }
    }


    /**
     * Action performed when you click on the poster to "see more" about a movie.
     * @param movieTitle arrays of movie title
     */
    public void seeMoreAction(String[] movieTitle) {
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
            AlertUtils.showErrorAlert("Error while trying to open the description page : " + e.getMessage());
        } catch (MovieNotFoundException e) {
            AlertUtils.showErrorAlert("The movie \"" + movieTitle[0] + "\" was not found in the database.");
        }

        if (movieDetails == null) {
            AlertUtils.showErrorAlert("The movie \"" + movieTitle[0] + "\" was not found in the database.");
            return;
        }
        controller.setMovieDetails(movieDetails);
        stage.showAndWait();
    }

}
