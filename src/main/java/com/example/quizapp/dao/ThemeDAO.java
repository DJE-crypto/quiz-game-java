package com.example.quizapp.dao;

import com.example.quizapp.DBConnection;
import com.example.quizapp.model.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeDAO {

    public List<Theme> findAll() throws SQLException {
        List<Theme> themes = new ArrayList<>();

        String sql = "SELECT id_theme, nom_theme FROM theme";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Theme t = new Theme(
                        rs.getInt("id_theme"),
                        rs.getString("nom_theme")
                );
                themes.add(t);
            }
        }
        return themes;
    }
}
