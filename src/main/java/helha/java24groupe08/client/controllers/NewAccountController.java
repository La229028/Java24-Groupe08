package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.User;
import helha.java24groupe08.client.models.exceptions.DatabaseException;
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
public class NewAccountController extends Application{

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
            ErrorUtils.showErrorAlert("An error occurred while loading the new account view: " + e.getMessage());
        }
    }

    public static void createUserAccount(String name, String firstname, int numberPhone, String email, int age, String status, String username, String password) {
        try {
            User newUser = new User(name, firstname, numberPhone, email, age, status, username, password);
            UserDBController.addUser(newUser);
        } catch (DatabaseException e) {
            ErrorUtils.showErrorAlert("An error occurred while creating the new user account: " + e.getMessage());
        }
    }
}
