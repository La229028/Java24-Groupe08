package helha.java24groupe08.controllers;

<<<<<<<< HEAD:src/main/java/helha/java24groupe08/controllers/IndexController.java
import com.java24groupe08.views.IndexViewController;
import com.java24groupe08.views.InfoViewsController;
========
import helha.java24groupe08.views.IndexViewController;
>>>>>>>> e9b828fdd1518b30045ac0a27e801e5fb20b2538:src/main/java/helha/java24groupe08/controllers/IndexApplication.java
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

<<<<<<<< HEAD:src/main/java/helha/java24groupe08/controllers/IndexController.java
public class IndexController extends Application {
    //private Button button;

========
public class IndexApplication extends Application {
>>>>>>>> e9b828fdd1518b30045ac0a27e801e5fb20b2538:src/main/java/helha/java24groupe08/controllers/IndexApplication.java
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexViewController.class.getResource("index.fxml"));
        //FXMLLoader fxmlLoaderInfo = new FXMLLoader(InfoViewsController.class.getResource("info.fxml"));
        Scene indexScene = new Scene(fxmlLoader.load());
        //Scene infoScene = new Scene(fxmlLoaderInfo.load());


<<<<<<<< HEAD:src/main/java/helha/java24groupe08/controllers/IndexController.java
        stage.setTitle("Cinéma");
        //stage.setScene(infoScene);

        stage.setScene(indexScene);
========
        stage.setTitle("CINEMA");
        stage.setScene(scene);
>>>>>>>> e9b828fdd1518b30045ac0a27e801e5fb20b2538:src/main/java/helha/java24groupe08/controllers/IndexApplication.java
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}