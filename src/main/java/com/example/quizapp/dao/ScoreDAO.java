package com.example.quizapp.dao;

import com.example.quizapp.DBConnection;
import com.example.quizapp.model.Score;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDAO {

    public void save(Score score) throws SQLException {
        String sql = "INSERT INTO score (valeur, date_score, utilisateur_id, quiz_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, score.getValeur());
            ps.setDate(2, Date.valueOf(score.getDateScore()));
            ps.setInt(3, score.getUtilisateur().getIdUtilisateur());
            ps.setInt(4, score.getQuiz().getIdQuiz());

            ps.executeUpdate();
        }
    }
}
