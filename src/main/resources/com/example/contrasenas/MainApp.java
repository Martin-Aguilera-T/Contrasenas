package com.example.contrasenas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la vista del Login (hello-view.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/example/contrasenas/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 400);

        // Enlaza la hoja de estilos global
        String css = MainApp.class.getResource("/com/example/contrasenas/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Título de la ventana principal
        stage.setTitle("Ferretería Calderón - Control de Acceso");
        stage.setScene(scene);
        stage.setResizable(false); // Evita que la ventana de login se deforme
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}