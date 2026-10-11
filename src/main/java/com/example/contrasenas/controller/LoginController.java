package com.example.contrasenas.controller;

import com.example.contrasenas.dao.UsuarioDAO;
import com.example.contrasenas.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void handleLogin(ActionEvent event) {
        String user = txtUsername.getText().trim();
        String pass = txtPassword.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Por favor llena todos los campos.");
            return;
        }

        Usuario usuarioAutenticado = usuarioDAO.autenticar(user, pass);

        if (usuarioAutenticado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/contrasenas/main-menu-view.fxml"));
                Parent root = loader.load();

                MainMenuController menuController = loader.getController();
                menuController.setUsuarioLogueado(usuarioAutenticado);

                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(new Scene(root, 900, 600));
                stage.setTitle("Ferretería - Menú Principal");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lblError.setText("Usuario o contraseña incorrectos.");
        }
    }
}