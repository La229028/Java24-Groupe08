module helha.java24groupe08 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;



    exports helha.java24groupe08.client.controllers;
    exports helha.java24groupe08.client.views;
    opens helha.java24groupe08.client.views to javafx.fxml;

    requires com.google.gson;
    requires java.sql;
    requires java.desktop;
    opens helha.java24groupe08.client.models to com.google.gson;
    exports helha.java24groupe08.client.models;

    opens helha.java24groupe08.client.controllers to javafx.fxml;


}