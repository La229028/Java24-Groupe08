package helha.java24groupe08.views;

import helha.java24groupe08.controllers.UserDBController;
import helha.java24groupe08.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * NewAccountViewController class is responsible for handling user interactions in the "New Account" window.
 * It provides methods to display the "New Account" window, handle the "Confirm" and "Cancel" buttons, and validate user input.
 */
public class NewAccountViewController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField numberPhoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;


    /**
     * Displays the "New Account" window.
     * @throws IOException If an input or output exception occurred
     */
    public static void showNewAccountWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(NewAccountViewController.class.getResource("newAccount.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("New Account");

        primaryStage.setResizable(false);
        primaryStage.show();
    }


    /**
     * Handles the "Confirm" button click event.
     * Validates the user input, creates a new User object, adds it to the database, and closes the "New Account" window.
     * @param event The ActionEvent object representing the button click event
     */
    public void handleConfirm(javafx.event.ActionEvent event) {
        if(!validateInput()){
            return;
        }

        String name = nameTextField.getText();
        String firstname = firstnameTextField.getText();
        String numberPhone = numberPhoneTextField.getText();
        String email = emailTextField.getText();
        int age = Integer.parseInt(ageTextField.getText());
        String status = statusChoiceBox.getValue();
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        User newUser = new User(name, firstname, numberPhone, email, age, status, username, password);

        UserDBController.addUser(newUser);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Validates the user input in the "New Account" window.
     * Checks if all fields are filled and if the age is a number.
     * @return true if the input is valid, false otherwise
     */
    private boolean validateInput(){
        boolean isValid = true;

        if(nameTextField.getText().isEmpty() || firstnameTextField.getText().isEmpty() || numberPhoneTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || ageTextField.getText().isEmpty() || statusChoiceBox.getValue() == null || usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()){
            showAlert("Please fill in all fields !");
            isValid = false;
        }

        try{
            Integer.parseInt(ageTextField.getText());
        }catch(NumberFormatException e){
            showAlert("Age must be a number !");
            isValid = false;
        }

        return isValid;
    }

    /**
     * Handles the "Cancel" button click event.
     * Closes the "New Account" window.
     * @param event The ActionEvent object representing the button click event
     */
    public void handleCancel(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Displays a warning alert with the specified message.
     * @param message The message to be displayed in the alert
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
