package com.java24groupe08.controllers;

import com.java24groupe08.views.IndexViewController;
import com.java24groupe08.views.InfoViewsController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexController extends Application {
    //private Button button;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexViewController.class.getResource("index.fxml"));
        //FXMLLoader fxmlLoaderInfo = new FXMLLoader(InfoViewsController.class.getResource("info.fxml"));
        Scene indexScene = new Scene(fxmlLoader.load());
        //Scene infoScene = new Scene(fxmlLoaderInfo.load());


        stage.setTitle("Cinéma");
        //stage.setScene(infoScene);

        stage.setScene(indexScene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}