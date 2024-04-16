//package helha.java24groupe08.controllers;
//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//
///**
// * Controller for the LoginView.
// * This class is responsible for handling user interactions with the LoginView,
// * and updating the view based on user input.
// */
//public class LoginApplication{
//
//    @FXML
//    public Button returnButton;
//    @FXML
//    public Button connectButton;
//    @FXML
//    public TextField usernameField;
//    @FXML
//    public TextField passwordField;
//
//    /**
//     * Initializes the controller class. This method is automatically called
//     * after the fxml file has been loaded.
//     *
//     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
//     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
//     */
////    @Override
////    public void initialize(URL url, ResourceBundle resourceBundle) {
////        returnButton.setOnAction(event -> returnButtonAction());
////        connectButton.setOnAction(event -> connectButtonAction());
////    }
//
//    /**
//     * Closes the login view when the return button is clicked.
//     */
//    public void returnButtonAction() {
//        try{
//            // Close the current stage
//            Stage currentStage = (Stage) returnButton.getScene().getWindow();
//            currentStage.hide();
//        }catch (Exception e) {
//            System.err.println("Error closing the window : " + e.getMessage());
//            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while closing the window : " + e.getMessage());
//        }
//    }
//
//    /**
//     * Checks the entered username and password when the connect button is clicked.
//     * If the username and password are correct, an information alert is shown.
//     * If the username and password are incorrect, an error alert is shown.
//     */
//    public void connectButtonAction() {
//        try {
//            String username = usernameField.getText();
//            String password = passwordField.getText();
//
//            if (username.equals("admin") && password.equals("admin")) {
//                // Display successful connection alert
//                showAlert(Alert.AlertType.INFORMATION, "Successful connection", "You are logged in as administrator");
//
//                // Close login window
//                Stage currentStage = (Stage) connectButton.getScene().getWindow();
//                currentStage.close();
//                // Open index page with "admin" label
//                //openIndexPageAsAdmin();
//
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Connection failed", "Incorrect user name or password");
//            }
//        }catch (Exception e) {
//            System.err.println("Error while trying to connect : " + e.getMessage());
//            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while trying to connect : " + e.getMessage());
//        }
//    }
//
//    /**
//     * Shows an alert with the specified type, title, and content.
//     *
//     * @param alertType The type of the alert.
//     * @param title The title of the alert.
//     * @param content The content of the alert.
//     */
//    private void showAlert(Alert.AlertType alertType, String title, String content){
//        try {
//            Alert alert = new Alert(alertType);
//            alert.setTitle(title);
//            alert.setHeaderText(null);
//            alert.setContentText(content);
//            alert.showAndWait();
//        } catch (Exception e) {
//            System.err.println("Error showing alert : " + e.getMessage());
//        }
//    }
//
//
//}
//
