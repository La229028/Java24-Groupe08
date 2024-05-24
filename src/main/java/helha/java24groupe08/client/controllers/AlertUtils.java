package helha.java24groupe08.client.controllers;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertUtils {
    /**
     * Shows an error alert with the given message.
     * @param message the message to display
     */
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

    /**
     * Shows an information alert with the given message.
     * @param message the message to display
     */
    public static void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
