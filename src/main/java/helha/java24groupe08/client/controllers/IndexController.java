package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.*;
import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.exceptions.MovieNotFoundException;
import helha.java24groupe08.client.views.DescriptionViewController;
import helha.java24groupe08.client.views.IndexViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
        launch(args);  // Assuming this is part of your JavaFX application startup
    }

    /**
     * Initializes the main stage of the application.
     *
     * @param stage The primary stage of the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            this.indexStage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/index.fxml"));
            Parent root = fxmlLoader.load();
            IndexViewController controller = fxmlLoader.getController();
            controller.setListener(this);
            stage.setTitle("Cinéma");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            ErrorUtils.showErrorAlert("Error while loading the main view: " + e.getMessage());
            stage.close();
        }
    }

    public void setIndexStage(Stage stage) {
        this.indexStage = stage;
    }

    /**
     * Action performed when the login button is clicked.
     */
    @Override
    public void loginButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            ErrorUtils.showErrorAlert("Error while loading the login view: " + e.getMessage());
        }
    }

    /**
     * Action performed when you click on the poster to "see more" about a movie.
     * @param movieTitle arrays of movie title
     */
    @Override
    public void seeMoreAction(String[] movieTitle) {
        String[] movieDetails = null;
        Stage stage = null;
        DescriptionViewController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/descrip.fxml"));
            Parent root = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Description");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.indexStage); // Use indexStage instead of this.indexStage.getOwner()
            controller = loader.getController();
            movieDetails = this.movieDBController.getMovie(movieTitle[0]);
        } catch (IOException e) {
            ErrorUtils.showErrorAlert("Error while trying to open the description page : " + e.getMessage());
        } catch (MovieNotFoundException e) {
            ErrorUtils.showErrorAlert("The movie \"" + movieTitle[0] + "\" was not found in the database.");
        }

        if (movieDetails == null) {
            ErrorUtils.showErrorAlert("The movie \"" + movieTitle[0] + "\" was not found in the database.");
            return;
        }
        controller.setMovieDetails(movieDetails);
        stage.showAndWait();
    }
}

