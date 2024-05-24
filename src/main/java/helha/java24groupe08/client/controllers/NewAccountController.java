package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.User;
import helha.java24groupe08.client.models.exceptions.DatabaseException;
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

    /**
     * Adds a new user to the database.
     *
     * @param name The name of the user.
     * @param firstname The first name of the user.
     * @param numberPhone The phone number of the user.
     * @param email The email of the user.
     * @param age The age of the user.
     * @param status The status of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @throws DatabaseException If an error occurred while adding the user to the database.
     */
    public static void AddUser(String name, String firstname, int numberPhone, String email, int age, String status, String username, String password) throws DatabaseException {
        try {
            User newUser = new User(name, firstname, numberPhone, email, age, status, username, password);

            UserDBController.addUser(newUser);
            AlertUtils.showInfoAlert("User added successfully.");
        } catch (DatabaseException e) {
            AlertUtils.showErrorAlert("An error occurred while adding the user: " + e.getMessage());
        }
    }

    /**
     * This method is called when the application is launched.
     * It loads the newAccount.fxml file and displays the new account window.
     */
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

    /**
     * This method is called when the application is launched.
     * It loads the newAccount.fxml file and displays the new account window.
     */
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
