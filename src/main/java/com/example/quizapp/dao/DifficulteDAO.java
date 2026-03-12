package com.example.quizapp.dao;

import com.example.quizapp.DBConnection;
import com.example.quizapp.model.Difficulte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DifficulteDAO {

    public List<Difficulte> findAll() throws SQLException {
        List<Difficulte> list = new ArrayList<>();

        String sql = "SELECT id_difficulte, niveau FROM difficulte ORDER BY id_difficulte ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Difficulte d = new Difficulte(
                        rs.getInt("id_difficulte"),
                        rs.getString("niveau")
                );
                list.add(d);
            }
        }
        return list;
    }
}
