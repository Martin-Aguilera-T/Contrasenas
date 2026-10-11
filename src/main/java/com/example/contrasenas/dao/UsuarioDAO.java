package com.example.contrasenas.dao;

import com.example.contrasenas.config.DatabaseConnection;
import com.example.contrasenas.model.Usuario;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Validar Login (compara la contraseña usando SHA-512)
    public Usuario autenticar(String userId, String password) {
        // Genera el hash SHA-512 y lo fuerza a minúsculas
        String hash = DigestUtils.sha512Hex(password).toLowerCase();

        // Compara el hash en minúsculas en la base de datos
        String sql = "select u.userId, u.role from usuarios u where u.userId = ? and lower(u.password) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId.trim());
            pstmt.setString(2, hash);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setUserId(rs.getString("userId"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insertar un nuevo usuario con SHA-512 y AES_ENCRYPT
    public boolean agregarUsuario(Usuario u) {
        String sql = "insert into usuarios (userId, firstName, lastName, emailAddress, userStatus, role, password, curp, rfc, specialty, notes, phoneEncrypted, addressEncrypted, salaryEncrypted) " +
                "values (?, ?, ?, ?, ?, ?, sha2(?, 512), ?, ?, ?, ?, aes_encrypt(?, ?), aes_encrypt(?, ?), aes_encrypt(?, ?))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, u.getUserId());
            pstmt.setString(2, u.getFirstName());
            pstmt.setString(3, u.getLastName());
            pstmt.setString(4, u.getEmailAddress());
            pstmt.setString(5, u.getUserStatus());
            pstmt.setString(6, u.getRole());
            pstmt.setString(7, u.getPassword());
            pstmt.setString(8, u.getCurp());
            pstmt.setString(9, u.getRfc());
            pstmt.setString(10, u.getSpecialty());
            pstmt.setString(11, u.getNotes());

            // Teléfono, dirección y sueldo cifrados
            pstmt.setString(12, u.getPhone());
            pstmt.setString(13, DatabaseConnection.getAesKey());
            pstmt.setString(14, u.getAddress());
            pstmt.setString(15, DatabaseConnection.getAesKey());
            pstmt.setString(16, u.getSalary());
            pstmt.setString(17, DatabaseConnection.getAesKey());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Consultar lista de usuarios diferenciando si es Jefe o Empleado
    public List<Usuario> listarUsuarios(boolean esJefe) {
        List<Usuario> lista = new ArrayList<>();
        String sql;

        if (esJefe) {
            sql = "select u.userId, u.firstName, u.lastName, u.emailAddress, u.userStatus, u.role, u.createdDate, u.curp, u.rfc, u.specialty, u.notes, " +
                    "cast(aes_decrypt(u.phoneEncrypted, ?) as char) as phone, " +
                    "cast(aes_decrypt(u.addressEncrypted, ?) as char) as address, " +
                    "cast(aes_decrypt(u.salaryEncrypted, ?) as char) as salary from usuarios u";
        } else {
            sql = "select u.userId, u.firstName, u.lastName, u.emailAddress, u.userStatus, u.role, u.createdDate, u.curp, u.rfc, u.specialty, u.notes, " +
                    "'*** OCULTO ***' as phone, '*** OCULTO ***' as address, '*** OCULTO ***' as salary from usuarios u";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (esJefe) {
                pstmt.setString(1, DatabaseConnection.getAesKey());
                pstmt.setString(2, DatabaseConnection.getAesKey());
                pstmt.setString(3, DatabaseConnection.getAesKey());
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setUserId(rs.getString("userId"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmailAddress(rs.getString("emailAddress"));
                user.setUserStatus(rs.getString("userStatus"));
                user.setRole(rs.getString("role"));
                user.setCreatedDate(rs.getTimestamp("createdDate"));
                user.setCurp(rs.getString("curp"));
                user.setRfc(rs.getString("rfc"));
                user.setSpecialty(rs.getString("specialty"));
                user.setNotes(rs.getString("notes"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setSalary(rs.getString("salary"));

                lista.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}