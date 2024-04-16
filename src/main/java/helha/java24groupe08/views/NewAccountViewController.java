package helha.java24groupe08.views;

import helha.java24groupe08.controllers.UserDBController;
import helha.java24groupe08.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    private PasswordField passwordField;



    public static void showNewAccountWindow() throws IOException {
        // Load the newAccount.fxml file using the FXMLLoader
        FXMLLoader loader = new FXMLLoader(NewAccountViewController.class.getResource("newAccount.fxml"));
        Parent root = loader.load();

        // Configures the stage and displays it (the new account window)
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("New Account");

        primaryStage.setResizable(false);//locks window size
        primaryStage.show();
    }


    public void handleConfirm(javafx.event.ActionEvent event) {
        String nom = nameTextField.getText();
        String prenom = firstnameTextField.getText();
        String telephone = numberPhoneTextField.getText();
        String email = emailTextField.getText();
        int age = Integer.parseInt(ageTextField.getText());
        String status = statusChoiceBox.getValue();
        String password = passwordField.getText();

        // Créez un nouvel utilisateur avec ces valeurs
        User newUser = new User(nom, prenom, telephone, email, age, status, password);

        // Ajoutez le nouvel utilisateur à votre base de données
        // Vous devrez remplacer cette ligne par le code approprié pour ajouter un utilisateur à votre base de données
        UserDBController.addUser(newUser);

        // Fermez la fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleCancel(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
