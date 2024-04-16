package helha.java24groupe08.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    /**
     * This method is called when the "Login" button is clicked.
     * It checks if the username and password are correct, and displays an error message if they are not.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("admin")) {
            // Print a message to indicate that the login was successful
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connexion réussie");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes connecté en tant qu'administrateur.");
            alert.showAndWait();

            // Call the onLoginSuccess method to close the login window (and possibly do other things)
            onLoginSuccess();
        } else {
            // Print an error message if the login failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
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

}
