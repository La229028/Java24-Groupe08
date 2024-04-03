package com.java24groupe08.controllers;

import com.java24groupe08.views.IndexViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexViewController.class.getResource("index.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("Cinéma");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}