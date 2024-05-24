package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.ErrorUtils;
import helha.java24groupe08.client.controllers.NewAccountController;
import helha.java24groupe08.client.models.UserSession;
import helha.java24groupe08.client.models.exceptions.DatabaseException;
import helha.java24groupe08.client.models.User;
import helha.java24groupe08.client.controllers.UserDBController;

import helha.java24groupe08.server.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

        Client client = new Client(username);
        client.connectToServer();
    }

    /**
     * Handles the login for an admin user.
     * Checks if the username and password are correct, and displays an error message if they are not.
     * @param username The username entered by the user
     * @param password The password entered by the user
     */
    private void handleLoginAdmin(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            isAdminLoggedIn = true; // Granting administrator rights
            ErrorUtils.showInfoAlert("Successful connection. Welcome admin!");
            onLoginSuccess();
        } else {
            ErrorUtils.showErrorAlert("Connection error. Please check your username and password admin.");
        }
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
            ErrorUtils.showInfoAlert("Successful connection. Welcome " + username + "!");
            isAdminLoggedIn = false;
            onLoginSuccess();
            UserSession.getInstance().setUser(user);

            IndexViewController indexViewController = IndexViewController.getInstance();
            if(indexViewController != null) {
                indexViewController.updateCartButtonVisibility();
            }
        } else {
            ErrorUtils.showErrorAlert("Connection error. Please check your username and password.");
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
        NewAccountController.showNewAccountWindow();
    }


    /**
     * This method is called when the "Forgot Password" button is clicked.
     * It displays a message box with the password.
     */
    public static boolean isAdminLoggedIn() {
        return isAdminLoggedIn;
    }

}
