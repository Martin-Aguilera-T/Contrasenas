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
    @FXML private Button btnGuardar;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private boolean esModificacion = false;

    @FXML
    public void initialize() {
        cmbRole.getItems().setAll("Jefe", "Empleado");
        cmbRole.getSelectionModel().selectFirst();

        cmbStatus.getItems().setAll("Active", "Inactive");
        cmbStatus.getSelectionModel().selectFirst();
    }
    public void cargarDatosParaModificar(Usuario usuario) {
        if (usuario == null) return;

        esModificacion = true;
        txtUserId.setText(usuario.getUserId());
        txtUserId.setEditable(false);

        txtFirstName.setText(usuario.getFirstName());
        txtLastName.setText(usuario.getLastName());
        txtEmail.setText(usuario.getEmailAddress());
        cmbRole.setValue(usuario.getRole());
        txtCurp.setText(usuario.getCurp());
        txtRfc.setText(usuario.getRfc());
        txtSpecialty.setText(usuario.getSpecialty());
        cmbStatus.setValue(usuario.getUserStatus());
        txtPhone.setText(usuario.getPhone());
        txtAddress.setText(usuario.getAddress());
        txtSalary.setText(usuario.getSalary());
        txtNotes.setText(usuario.getNotes());

        if (btnGuardar != null) {
            btnGuardar.setText("ACTUALIZAR REGISTRO");
        }
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        if (txtUserId.getText().isBlank() || txtPassword.getText().isBlank() ||
                txtFirstName.getText().isBlank() || txtLastName.getText().isBlank() ||
                txtEmail.getText().isBlank()) {

            lblMensaje.setText("Llena todos los campos.");
            lblMensaje.getStyleClass().setAll("error-label");
            return;
        }

        if (!validarCampos()) {
            lblMensaje.getStyleClass().setAll("error-label");
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

        boolean resultado;
        if (esModificacion) {
            resultado = usuarioDAO.modificarUsuario(u);
        } else {
            resultado = usuarioDAO.agregarUsuario(u);
        }

        if (resultado) {
            lblMensaje.setText(esModificacion ? "Usuario Actualizado Correctamente" : "Usuario Registrado Correctamente");
            lblMensaje.getStyleClass().setAll("subtitle-label");
            limpiarCampos();
        } else {
            lblMensaje.setText("Error al guardar en la base de datos");
            lblMensaje.getStyleClass().setAll("error-label");
        }
    }

    private void limpiarCampos() {
        txtUserId.clear(); txtUserId.setEditable(true);
        txtPassword.clear(); txtFirstName.clear();
        txtLastName.clear(); txtEmail.clear(); txtCurp.clear();
        txtRfc.clear(); txtSpecialty.clear(); txtPhone.clear();
        txtAddress.clear(); txtSalary.clear(); txtNotes.clear();
        esModificacion = false;
        if (btnGuardar != null) {
            btnGuardar.setText("Guardar Registro");
        }
    }

    private boolean validarCampos() {
        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            lblMensaje.setText("Correo inválido.");
            return false;
        }
        if (!txtPhone.getText().isEmpty() && !txtPhone.getText().matches("\\d{10}")) {
            lblMensaje.setText("Teléfono inválido (debe tener 10 dígitos).");
            return false;
        }
        if (!txtSalary.getText().isEmpty()) {
            try {
                Double.parseDouble(txtSalary.getText());
            } catch (NumberFormatException e) {
                lblMensaje.setText("Sueldo inválido.");
                return false;
            }
        }
        if (txtUserId.getText().length() > 50) {
            lblMensaje.setText("UserId inválido (máximo 50 caracteres).");
            return false;
        }
        return true;
    }
}