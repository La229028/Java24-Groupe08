package helha.java24groupe08.client.controllers;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorUtils {
    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
