package com.example.quizapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL  =
            "jdbc:mysql://localhost:3306/quiz_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";   // utilisateur XAMPP
    private static final String PASS = "";       // mot de passe MySQL (mets ton mdp si tu en as un)

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }
}
