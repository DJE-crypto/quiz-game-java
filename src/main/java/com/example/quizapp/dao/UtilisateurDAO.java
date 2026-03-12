package com.example.quizapp.dao;

import com.example.quizapp.DBConnection;
import com.example.quizapp.model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UtilisateurDAO {

    // ----- 1) LOGIN : se connecter avec email + mot de passe -----
    public Utilisateur login(String email, String motDePasse) throws SQLException {
        String sql =
                "SELECT id_user, nom, email, password, role_id " +
                        "FROM utilisateur WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("password");

                    // Vérifier le mot de passe saisi
                    if (BCrypt.checkpw(motDePasse, hash)) {
                        Utilisateur u = new Utilisateur();
                        u.setIdUtilisateur(rs.getInt("id_user"));
                        u.setNom(rs.getString("nom"));
                        u.setEmail(rs.getString("email"));
                        // rôle admin si role_id==1
                        boolean admin = (rs.getInt("role_id") == 1);
                        u.setAdmin(admin);
                        return u;
                    } else {
                        return null; // mauvais mot de passe
                    }
                } else {
                    return null;     // email inconnu
                }
            }
        }
    }


    // ----- 2) Vérifier si un email existe déjà -----
    public boolean emailExists(String email) throws SQLException {

        String sql = "SELECT 1 FROM utilisateur WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();   // true si on trouve une ligne
            }
        }
    }

    // ----- 3) Enregistrer un nouvel utilisateur -----

    public void register(String nom, String email, String motDePasse) throws SQLException {
        String hashed = BCrypt.hashpw(motDePasse, BCrypt.gensalt());  // 🔐 hash ici

        String sql = "INSERT INTO utilisateur (nom, email, password, role_id) " +
                "VALUES (?, ?, ?, 2)";   // 2 = utilisateur normal

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, email);
            ps.setString(3, hashed);   // on stocke le HASH, pas le mot de passe
            ps.executeUpdate();
        }
    }

}
