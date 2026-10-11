package com.example.contrasenas.controller;

import com.example.contrasenas.dao.UsuarioDAO;
import com.example.contrasenas.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        List<Usuario> usuarios = usuarioDAO.listarUsuarios(esJefe);
        tblUsuarios.setItems(FXCollections.observableArrayList(usuarios));
    }
}