package com.example.contrasenas.controller;

import com.example.contrasenas.dao.UsuarioDAO;
import com.example.contrasenas.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class UserListController {

    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, String> colUserId;
    @FXML private TableColumn<Usuario, String> colFirstName;
    @FXML private TableColumn<Usuario, String> colLastName;
    @FXML private TableColumn<Usuario, String> colEmail;
    @FXML private TableColumn<Usuario, String> colStatus;
    @FXML private TableColumn<Usuario, String> colRole;
    @FXML private TableColumn<Usuario, Timestamp> colDate;
    @FXML private TableColumn<Usuario, String> colPhone;
    @FXML private TableColumn<Usuario, String> colAddress;
    @FXML private TableColumn<Usuario, String> colSalary;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private boolean esJefeActual = false;

    @FXML
    public void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("userStatus"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    public void cargarUsuarios(boolean esJefe) {
        this.esJefeActual = esJefe;
        List<Usuario> usuarios = usuarioDAO.listarUsuarios(esJefe);
        tblUsuarios.setItems(FXCollections.observableArrayList(usuarios));
    }

    @FXML
    private void handleModificarSeleccionado() {
        Usuario seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Seleccione un usuario.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/contrasenas/register-view.fxml"));
            Parent view = loader.load();

            RegisterController registerController = loader.getController();
            registerController.cargarDatosParaModificar(seleccionado);

            StackPane contentArea = (StackPane) tblUsuarios.getScene().lookup("#contentArea");
            if (contentArea != null) {
                contentArea.getChildren().setAll(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de edición.");
        }
    }

    @FXML
    private void handleEliminarSeleccionado() {
        Usuario seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Seleccione un usuario.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar al usuario: " + seleccionado.getUserId() + "?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            boolean exito = usuarioDAO.eliminarUsuario(seleccionado.getUserId());
            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente.");
                cargarUsuarios(esJefeActual);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el usuario.");
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}