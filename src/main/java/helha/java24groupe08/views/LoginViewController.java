package helha.java24groupe08.views;

import helha.java24groupe08.controllers.LoginApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    private LoginApplication controller;

    public void setController(LoginApplication controller) {
        this.controller = controller;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnButton.setOnAction(event -> controller.returnButtonAction());
        connectButton.setOnAction(event -> controller.connectButtonAction());
    }
}