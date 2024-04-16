package helha.java24groupe08.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApplication extends Application {
    /**
     * This method is called when the application is launched.
     * It loads the login.fxml file and displays the login window.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login.fxml file using the FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Configures the stage and displays it (the login window)
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }


    /**
     * This method is called when the application is launched.
     * It loads the login.fxml file and displays the login window.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
