package helha.java24groupe08.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the LoginView.
 * This class is responsible for handling user interactions with the LoginView,
 * and updating the view based on user input.
 */
public class LoginViewController implements Initializable {
    @FXML
    public Button returnButton;

    @FXML
    public Button connectButton;

    @FXML
    public TextField usernameField;

    @FXML
    public TextField passwordField;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnButton.setOnAction(event -> {
            try{
                returnButtonAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        connectButton.setOnAction(event -> connectButtonAction());
    }

    /**
     * Closes the login view when the return button is clicked.
     */
    private void returnButtonAction() throws IOException {
            Stage currentStage = (Stage) returnButton.getScene().getWindow();
            currentStage.hide();
    }

    /**
     * Checks the entered username and password when the connect button is clicked.
     * If the username and password are correct, an information alert is shown.
     * If the username and password are incorrect, an error alert is shown.
     */
    private void connectButtonAction() {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("admin")) {
                showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Vous êtes connecté en tant qu'administrateur");
            } else {
                showAlert(Alert.AlertType.ERROR, "Connexion échouée", "Nom d'utilisateur ou mot de passe incorrect");
            }
    }

    /**
     * Shows an alert with the specified type, title, and content.
     *
     * @param alertType The type of the alert.
     * @param title The title of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String content){
        try {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText(null);
            alert.setContentText("L'erreur est : " + e.getMessage());
            alert.showAndWait();
        }
    }


}
