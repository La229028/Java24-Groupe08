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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helha/java24groupe08/views/index.fxml"));

            Stage currentStage = (Stage) returnButton.getScene().getWindow();
            currentStage.close();
    }

    private void connectButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("admin")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bienvenue");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes connecté en tant qu'admin");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect");
            alert.showAndWait();
        }
    }

}
