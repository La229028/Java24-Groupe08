package helha.java24groupe08.client.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {

    /**
     * This method is called when the application is launched.
     * It loads the login.fxml file and displays the login window.
     */
    @Override
    public void start(Stage primaryStage){
        try{
            // Load the login.fxml file using the FXMLLoader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Configures the stage and displays it (the login window)
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch(IOException e) {
            this.showErrorAlert("An error occurred while loading the login view : " + e.getMessage());
        }
    }


    /**
     * This method is called when the application is launched.
     * It loads the login.fxml file and displays the login window.
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
