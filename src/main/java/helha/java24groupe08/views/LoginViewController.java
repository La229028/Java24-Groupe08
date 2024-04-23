package helha.java24groupe08.views;

import helha.java24groupe08.models.User;
import helha.java24groupe08.controllers.UserDBController;

import helha.java24groupe08.models.exceptions.DatabaseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private static boolean isAdminLoggedIn = false;


    /**
     * This method is called when the "Login" button is clicked.
     * It checks if the username and password are correct, and displays an error message if they are not.
     */
    @FXML
    private void handleLogin(ActionEvent event) throws DatabaseException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin")) {
            handleLoginAdmin(username, password);
        } else {
            handleLoginUser(username, password);
        }
    }

    /**
     * Handles the login for an admin user.
     * Checks if the username and password are correct, and displays an error message if they are not.
     * @param username The username entered by the user
     * @param password The password entered by the user
     */
    private void handleLoginAdmin(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            isAdminLoggedIn = true; // Accorder les droits administrateurs
            showAlert("Successful connection", "You are logged in as administrator.", Alert.AlertType.INFORMATION);
            onLoginSuccess();
        } else {
            showAlert("Connection error", "Incorrect username or password.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the login for a regular user.
     * Checks if the username and password are correct, and displays an error message if they are not.
     * @param username The username entered by the user
     * @param password The password entered by the user
     */
    private void handleLoginUser(String username, String password) throws DatabaseException {
        UserDBController userDBController = new UserDBController();
        User user = userDBController.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful connection");
            alert.setHeaderText(null);
            alert.setContentText("Welcome " + user.getFirstname() + " !");
            alert.showAndWait();
            isAdminLoggedIn = false;
            onLoginSuccess();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password.");
            alert.showAndWait();
        }
    }


    /**
     * This method is called when the "Cancel" button is clicked.
     * It closes the login window.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        // Close the login window
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * This method is called when the login is successful.
     * It closes the login window.
     */
    private void onLoginSuccess() {
        // Close the login window
        usernameField.getScene().getWindow().hide();
    }

    /**
     * This method is called when the "New Account" button is clicked.
     * It closes the login window and opens the "New Account" window.
     * @param event The ActionEvent object representing the button click event
     * @throws IOException If an input or output exception occurred
     */
    @FXML
    private void handleNewAccount(ActionEvent event) throws IOException {
        ((Button) event.getSource()).getScene().getWindow().hide();
        NewAccountViewController.showNewAccountWindow();
    }


    /**
     * This method is called when the "Forgot Password" button is clicked.
     * It displays a message box with the password.
     */
    public static boolean isAdminLoggedIn() {
        return isAdminLoggedIn;
    }

}
