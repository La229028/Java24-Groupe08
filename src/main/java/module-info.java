module com.example.java24groupe08 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;




    exports helha.java24groupe08.controllers;
    exports helha.java24groupe08.views;
    opens helha.java24groupe08.views to javafx.fxml;

    requires com.google.gson;
    requires java.sql;
    exports helha.java24groupe08.models to com.google.gson;
    opens helha.java24groupe08.models to com.google.gson;
}