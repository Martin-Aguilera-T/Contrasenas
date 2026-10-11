package com.example.contrasenas.controller;

import com.example.contrasenas.model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainMenuController {

    @FXML private MenuItem menuItemEditar;
    @FXML private StackPane contentArea;

    private Usuario usuarioLogueado;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;

        // Si es Empleado, deshabilita la opción de Agregar/Editar
        if ("Empleado".equalsIgnoreCase(usuario.getRole())) {
            menuItemEditar.setDisable(true);
        }
    }

    @FXML
    private void handleVerUsuarios(ActionEvent event) {
        cargarVista("/com/example/contrasenas/user-list-view.fxml", true);
    }

    @FXML
    private void handleAgregarUsuario(ActionEvent event) {
        cargarVista("/com/example/contrasenas/register-view.fxml", false);
    }

    private void cargarVista(String fxmlPath, boolean esLista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent vista = loader.load();

            if (esLista) {
                UserListController controller = loader.getController();
                controller.cargarUsuarios("Jefe".equalsIgnoreCase(usuarioLogueado.getRole()));
            }

            contentArea.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        Platform.exit();
    }
}