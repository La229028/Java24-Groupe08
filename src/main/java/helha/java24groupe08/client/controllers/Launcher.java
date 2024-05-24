package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.views.IndexViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Launcher class initializes and starts the JavaFX application.
 */
public class Launcher extends Application {

    /**
     * The main method of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Ensure DatabaseConnection instance is created which will initialize the database
        DatabaseConnection.getInstance();

        System.out.println("Database initialized successfully.");
        launch(args);  // Launch the JavaFX application
    }

    /**
     * Initializes the main stage of the application.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/index.fxml"));
            Parent root = fxmlLoader.load();
            IndexViewController controller = fxmlLoader.getController();
            IndexController indexController = new IndexController();
            indexController.setIndexStage(primaryStage); // Ensure indexStage is set
            controller.setListener(indexController);
            primaryStage.setTitle("Cinéma");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            ErrorUtils.showErrorAlert("Error while loading the main view: " + e.getMessage());
            primaryStage.close();
        }
    }
}
