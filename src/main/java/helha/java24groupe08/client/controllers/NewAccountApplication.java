package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.views.NewAccountViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is the entry point of the application.
 * It loads the newAccount.fxml file and displays the new account window.
 */
public class NewAccountApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try{
            FXMLLoader loader = new FXMLLoader(NewAccountViewController.class.getResource("newAccount.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("New Account");

            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText("An error occurred while loading the new account view: " + e.getMessage());
            alert.showAndWait();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
