package com.example.contrasenas.controller;

import com.example.contrasenas.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainMenuController {

    @FXML private StackPane contentArea;
    private Usuario usuarioLogueado;
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML
    private void handleVerUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/contrasenas/user-list-view.fxml"));
            Parent view = loader.load();

            UserListController controller = loader.getController();
            boolean esJefe = usuarioLogueado != null && "Jefe".equalsIgnoreCase(usuarioLogueado.getRole());
            controller.cargarUsuarios(esJefe);

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAgregarUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/contrasenas/register-view.fxml"));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalir() {
        System.exit(0);
    }
}