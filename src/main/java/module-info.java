module com.example.contrasenas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.codec;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.contrasenas to javafx.fxml;
    opens com.example.contrasenas.controller to javafx.fxml;
    opens com.example.contrasenas.model to javafx.base;

    exports com.example.contrasenas;
    exports com.example.contrasenas.controller;
    exports com.example.contrasenas.model;
    exports com.example.contrasenas.dao;
}