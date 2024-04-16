package helha.java24groupe08.controllers;

import helha.java24groupe08.models.MovieDBController;
import helha.java24groupe08.views.DescriptionViewController;
import helha.java24groupe08.views.IndexViewController;
import helha.java24groupe08.views.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;


public class IndexApplication extends Application implements IndexViewController.Listener {

    private Stage indexStage;
    private static final String ERROR_TITLE = "Error";
    private static final String ERROR_HEADER = "An error occurred";

    private MovieDBController movieDBController = new MovieDBController();
    private MovieAPIController movieAPIController = new MovieAPIController();

    @Override
    public void start(Stage stage) throws Exception {
        this.indexStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/index.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        IndexViewController controller = fxmlLoader.getController();
        controller.setListener(this);

        stage.setTitle("Cinéma");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called when the login button is clicked.
     * It opens the login page in a new stage.
     */
    public void loginButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/login.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            LoginViewController controller = loader.getController();

            // Create an instance of LoginApplication
//            LoginApplication loginApp = new LoginApplication();
//
//            // Set the instance of LoginApplication to the controller
//            controller.setController(loginApp);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Log In");
            stage.setResizable(false);//page cannot be resized
            stage.initModality(Modality.APPLICATION_MODAL);//The page cannot be closed without logging in
            stage.initOwner(indexStage.getOwner());//The main page cannot be clicked
            stage.showAndWait();//The main page cannot be clicked until the login page is closed
        } catch (IOException e) {
            showErrorAlert("Error while trying to open the login page : " + e.getMessage());
        }
    }





    @Override
    public void seeMoreButtonAction(String[] movieTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/descrip.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Description");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(indexStage.getOwner());

            DescriptionViewController controller = loader.getController();

            // Get movie details from the database
            String[] movieDetails = movieDBController.getMovie(Arrays.toString(movieTitle));

            // If the movie is found, configure the description view controller with the movie details
            if (movieDetails != null) {
                controller.setMovieDetails(movieDetails);
            } else {
                showErrorAlert("The movie \"" + Arrays.toString(movieTitle) + "\" was not found in the database.");
                return;
            }

            stage.showAndWait();
        } catch (IOException e) {
            showErrorAlert("Error while trying to open the description page : " + e.getMessage());
        }
    }

    @Override
    public void seeMoreButtonAction(String movieTitle) {
        try {
            String[] movieDetails = movieDBController.getMovie(movieTitle);
            if (movieDetails != null) {
                // Pass to the description scene
                // SceneViewsController.switchToDescription(movieDetails, indexStage);
            } else {
                showErrorAlert("Movie not found in database: " + movieTitle);
            }
        } catch (Exception e) {
            showErrorAlert("Error for movie : " + movieTitle + " \n " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }

    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_TITLE);
        alert.setHeaderText(ERROR_HEADER);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void loadAndInsertMoviesFromAPI() {
        try {
            String[] titles = {"title1", "title2", "title3"}; // Replace with real movie titles to retrieve
            movieAPIController.getAndStoreMoviesFromApi(titles);
        } catch (Exception e) {
            showErrorAlert("An error occurred while loading and inserting movies from API: " + e.getMessage());
        }
    }
}
