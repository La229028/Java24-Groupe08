module com.example.java24groupe08 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    exports com.example.java24groupe08.controller;
    exports com.example.java24groupe08.view;
    opens com.example.java24groupe08.view to javafx.fxml;
}