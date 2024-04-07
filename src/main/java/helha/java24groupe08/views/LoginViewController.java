package helha.java24groupe08.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {
    @FXML
    public Button returnButton;

    @FXML
    public Button connectButton;

    @FXML
    public TextField usernameField;

    @FXML
    public TextField passwordField;

    public void initialize() {
        returnButton.setOnAction(event -> {
            try{
                returnButtonAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        connectButton.setOnAction(event -> connectButtonAction());
    }

    private void returnButtonAction() throws IOException {
            Stage currentStage = (Stage) returnButton.getScene().getWindow();
            currentStage.hide();
    }

    private void connectButtonAction() {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("admin")) {
                showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Vous êtes connecté en tant qu'administrateur");
            } else {
                showAlert(Alert.AlertType.ERROR, "Connexion échouée", "Nom d'utilisateur ou mot de passe incorrect");
            }
    }

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
