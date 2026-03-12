package com.example.quizapp.dao;

import com.example.quizapp.DBConnection;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    // 🔹 Récupérer les quiz d’un thème
    public List<Quiz> findByTheme(int themeId) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();

        String sql = "SELECT id_quiz, titre, date_creation, theme_id " +
                "FROM quiz WHERE theme_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, themeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idQuiz = rs.getInt("id_quiz");
                    String titre = rs.getString("titre");
                    Date dateSql = rs.getDate("date_creation");
                    LocalDate dateCreation = (dateSql != null) ? dateSql.toLocalDate() : null;

                    Theme theme = new Theme(rs.getInt("theme_id"), null);

                    Quiz q = new Quiz(idQuiz, titre, dateCreation, null, theme);
                    quizzes.add(q);
                }
            }
        }
        return quizzes;
    }


    public void delete(int idQuiz) throws SQLException {
        String sql = "DELETE FROM quiz WHERE id_quiz = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idQuiz);
            ps.executeUpdate();
        }
    }

    // 🔹 Sauvegarder un quiz (utilisé par l’admin)
    public void save(Quiz quiz) throws SQLException {
        String sql =
                "INSERT INTO quiz (titre, date_creation, utilisateur_id, theme_id) " +
                        "VALUES (?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, quiz.getTitre());

            LocalDate date = quiz.getDateCreation();
            if (date == null) {
                date = LocalDate.now();
            }
            ps.setDate(2, Date.valueOf(date));

            // adapte ce getter si besoin : id de l'utilisateur
            ps.setInt(3, quiz.getCreateur().getIdUtilisateur());

            // id du thème
            ps.setInt(4, quiz.getTheme().getIdTheme());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    quiz.setIdQuiz(rs.getInt(1));
                }
            }
        }
    }
}