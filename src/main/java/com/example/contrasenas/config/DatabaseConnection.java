package com.example.contrasenas.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://servidordordor.mysql.database.azure.com:3306/contrasenasBD?useSSL=true&requireSSL=false";
    private static final String USER = "admin1";
    private static final String PASSWORD = "Palos12345";

    private static final String AES_KEY = "contra";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getAesKey() {
        return AES_KEY;
    }
}