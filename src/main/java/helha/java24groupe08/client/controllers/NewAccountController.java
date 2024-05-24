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
public class NewAccountController extends Application {
    private static Stage primaryStage;

    public static void AddUser(String name, String firstname, int numberPhone, String email, int age, String status, String username, String password) throws DatabaseException {
        try {
            User newUser = new User(name, firstname, numberPhone, email, age, status, username, password);

            UserDBController.addUser(newUser);
            ErrorUtils.showInfoAlert("User added successfully.");
        } catch (DatabaseException e) {
            ErrorUtils.showErrorAlert("An error occurred while adding the user: " + e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/helha/java24groupe08/views/newAccount.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("New Account");

            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText("An error occurred while loading the new account view: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void showNewAccountWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(NewAccountController.class.getResource("/helha/java24groupe08/views/newAccount.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("New Account");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(NewAccountController.class, args);
    }
}
