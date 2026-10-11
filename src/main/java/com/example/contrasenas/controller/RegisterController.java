package com.example.contrasenas.controller;

import com.example.contrasenas.dao.UsuarioDAO;
import com.example.contrasenas.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField txtUserId, txtFirstName, txtLastName, txtEmail, txtCurp, txtRfc, txtSpecialty, txtPhone, txtAddress, txtSalary;
    @FXML private PasswordField txtPassword;
    @FXML private TextArea txtNotes;
    @FXML private ComboBox<String> cmbRole, cmbStatus;
    @FXML private Label lblMensaje;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        cmbRole.getItems().addAll("Jefe", "Empleado");
        cmbRole.getSelectionModel().selectFirst();

        cmbStatus.getItems().addAll("Active", "Inactive");
        cmbStatus.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        if (txtUserId.getText().isBlank() || txtPassword.getText().isBlank() ||
                txtFirstName.getText().isBlank() || txtLastName.getText().isBlank() ||
                txtEmail.getText().isBlank()) {

            lblMensaje.setText("Por favor llena los campos requeridos.");
            lblMensaje.setStyle("-fx-text-fill: #ff5252;");
            return;
        }

        Usuario u = new Usuario();
        u.setUserId(txtUserId.getText().trim());
        u.setPassword(txtPassword.getText().trim());
        u.setFirstName(txtFirstName.getText().trim());
        u.setLastName(txtLastName.getText().trim());
        u.setEmailAddress(txtEmail.getText().trim());
        u.setRole(cmbRole.getValue());
        u.setUserStatus(cmbStatus.getValue());
        u.setCurp(txtCurp.getText().trim());
        u.setRfc(txtRfc.getText().trim());
        u.setSpecialty(txtSpecialty.getText().trim());
        u.setNotes(txtNotes.getText().trim());
        u.setPhone(txtPhone.getText().trim());
        u.setAddress(txtAddress.getText().trim());
        u.setSalary(txtSalary.getText().trim());

        if (usuarioDAO.agregarUsuario(u)) {
            lblMensaje.setText("¡Usuario registrado con éxito!");
            lblMensaje.setStyle("-fx-text-fill: #4caf50;");
            limpiarCampos();
        } else {
            lblMensaje.setText("Error al registrar usuario en Azure.");
            lblMensaje.setStyle("-fx-text-fill: #ff5252;");
        }
    }

    private void limpiarCampos() {
        txtUserId.clear(); txtPassword.clear(); txtFirstName.clear();
        txtLastName.clear(); txtEmail.clear(); txtCurp.clear();
        txtRfc.clear(); txtSpecialty.clear(); txtPhone.clear();
        txtAddress.clear(); txtSalary.clear(); txtNotes.clear();
    }
    private boolean validarCampos() {
        // Validar correo electrónico
        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            lblMensaje.setText("El formato del correo es inválido.");
            return false;
        }
        // Validar teléfono (ej. 10 dígitos numéricos)
        if (!txtPhone.getText().matches("\\d{10}")) {
            lblMensaje.setText("El teléfono debe contener exactamente 10 dígitos.");
            return false;
        }
        // Validar sueldo numérico
        try {
            Double.parseDouble(txtSalary.getText());
        } catch (NumberFormatException e) {
            lblMensaje.setText("El sueldo debe ser un valor numérico.");
            return false;
        }
        // Validar longitud del UserId
        if (txtUserId.getText().length() > 50) {
            lblMensaje.setText("El User ID no puede exceder los 50 caracteres.");
            return false;
        }
        return true;
    }
}