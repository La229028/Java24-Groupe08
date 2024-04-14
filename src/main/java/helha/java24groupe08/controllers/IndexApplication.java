package helha.java24groupe08.controllers;

import helha.java24groupe08.models.Movie;
import helha.java24groupe08.views.IndexViewController;
import helha.java24groupe08.views.SceneViewsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

/**
 * This is the main class for the application.
 * It sets up the stage and scene, and loads the movie data.
 */
public class IndexApplication extends Application implements IndexViewController.Listener {

    private Stage indexStage;
    private static final String ERROR_TITLE = "Error";
    private static final String ERROR_HEADER = "An error occurred";

    /**
     * This method shows an error alert with the given content text.
     *
     * @param contentText The content text of the error alert.
     */
    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_TITLE);
        alert.setHeaderText(ERROR_HEADER);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * This method starts the application.
     * It sets up the stage and scene, and loads the movie data.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If there is an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.indexStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(IndexViewController.class.getResource("index.fxml"));
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
    @Override
    public void loginButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/login.fxml"));
            Parent root = loader.load();

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

    /**
     * This method is called when the "see more" button is clicked for a movie.
     * It switches the scene to the description page for the given movie.
     *
     * @param movie The movie for which the "see more" button was clicked.
     */
    @Override
    public void seeMoreButtonAction(Movie movie) {
        try {
            SceneViewsController.switchToDescription(movie,indexStage);
        } catch (IOException e) {
           showErrorAlert("Error for movie : " + movie.getTitle() + " \n " + e.getMessage());
        }
    }

    /**
     * The main method of the application.
     * It loads the movie data and launches the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            loadAndInsertMovies();
            launch();
        } catch (ClassNotFoundException e) {
            System.err.println("Error while loading the JDBC driver : "+ e.getMessage());
            System.exit(1);//exit the program, error code 1
        }catch (Exception e){
            System.err.println("An error has occurred : "+ e.getMessage());
            System.exit(1);
        }
    }

    /**
     * This method loads the movie data and inserts it into the database.
     * It throws an exception if the list of movies is null.
     *
     * @throws Exception If the list of movies is null.
     */
    private static void loadAndInsertMovies() throws Exception{
        List<Movie> movies = MovieController.loadMovieData();
        if (movies == null) {
            throw new Exception("The list of films loaded is null. Impossible to insert films in the database");
        }
        for(Movie movie : movies){
            MovieController.insertMoviesIntoDb(movie);
        }
    }
}