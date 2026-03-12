package com.example.quizapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Connexion OK : " + conn);

            Statement st = conn.createStatement();
            // adapte le nom de la table si besoin (utilisateur / users / etc.)
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS nb FROM utilisateur");
            if (rs.next()) {
                System.out.println("Nombre d'utilisateurs = " + rs.getInt("nb"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
