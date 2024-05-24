package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.UserSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            ErrorUtils.showErrorAlert("An error occurred while loading the login view: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void handleLogin() {
        // Perform login logic
        // ...

        // Start a new thread upon successful login
        UserSession userSession = UserSession.getInstance();
        if (userSession != null) {
            UserThread userThread = new UserThread(userSession);
            userThread.start();
        }
    }
}

class UserThread extends Thread {
    private UserSession userSession;

    public UserThread(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public void run() {
        // Logic for user session
        System.out.println("User thread running for: " + userSession.getUser().getUsername());
    }
}
