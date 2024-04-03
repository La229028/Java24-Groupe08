module com.example.java24groupe08 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;




    exports com.java24groupe08.controllers;
    exports com.java24groupe08.views;
    opens com.java24groupe08.views to javafx.fxml;

    requires com.google.gson;
    exports com.java24groupe08.models to com.google.gson;
    opens com.java24groupe08.models to com.google.gson;
}