package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.NewAccountController;
import helha.java24groupe08.client.models.exceptions.DatabaseException;
import helha.java24groupe08.client.controllers.UserDBController;
import helha.java24groupe08.client.models.User;
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
            int numberPhone = Integer.parseInt(numberPhoneTextField.getText());
            String email = emailTextField.getText();
            int age = Integer.parseInt(ageTextField.getText());
            String status = statusChoiceBox.getValue();
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            NewAccountController.createUserAccount(name, firstname, numberPhone, email, age, status, username, password);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
    }

    /**
     * Displays the "New Account" window.
     */
    private boolean validateInput(){
        return isAllFieldsFilled() && isAgeValid() && isPhoneNumberValid();
    }

    /**
     * Check the fields input
     * @return true if all fields are filled, false otherwise
     */
    private boolean isAllFieldsFilled(){
        if(nameTextField.getText().isEmpty() || firstnameTextField.getText().isEmpty() || numberPhoneTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || ageTextField.getText().isEmpty() || statusChoiceBox.getValue() == null || usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()){
            showAlert("Please fill in all fields !");
            return false;
        }
        return true;
    }

    /**
     * Check if the age is valid
     * @return true if the age is a number, false otherwise
     */
    private boolean isAgeValid(){
        try{
            Integer.parseInt(ageTextField.getText());
        }catch(NumberFormatException e){
            showAlert("Age must be a number !");
            return false;
        }
        return true;
    }

    /**
     * Check if the phone number is valid
     * @return true if the phone number is a number and has at least 5 digits, false otherwise
     */
    private boolean isPhoneNumberValid() {
        try{
            int phoneNumber = Integer.parseInt(numberPhoneTextField.getText());
            if(String.valueOf(phoneNumber).length() < 5){
                showAlert("Phone number must be at least 5 digits !");
                return false;
            }
        }catch(NumberFormatException e){
            showAlert("Phone number must be a number !");
            return false;
        }
        return true;
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
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
