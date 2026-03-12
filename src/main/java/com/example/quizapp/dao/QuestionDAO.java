package com.example.quizapp.dao;

import com.example.quizapp.DBConnection;
import com.example.quizapp.model.Difficulte;
import com.example.quizapp.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    /**
     * Récupère toutes les questions d'un quiz,
     * triées par difficulté (facile -> difficile) puis par id_question.
     */
    public List<Question> findByQuiz(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();

        String sql =
                "SELECT q.id_question, q.libelle, " +
                        "       q.choix1, q.choix2, q.choix3, q.choix4, " +
                        "       q.reponse, q.difficulte_id, d.niveau " +
                        "FROM question q " +
                        "JOIN difficulte d ON q.difficulte_id = d.id_difficulte " +
                        "WHERE q.quiz_id = ? " +
                        "ORDER BY q.difficulte_id ASC, q.id_question ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quizId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Difficulte diff = new Difficulte(
                            rs.getInt("difficulte_id"),
                            rs.getString("niveau")
                    );

                    Question q = new Question(
                            rs.getInt("id_question"),
                            rs.getString("libelle"),
                            rs.getString("choix1"),
                            rs.getString("choix2"),
                            rs.getString("choix3"),
                            rs.getString("choix4"),
                            rs.getInt("reponse"),
                            null,       // Quiz (optionnel ici)
                            diff
                    );

                    questions.add(q);
                }
            }
        }

        return questions;
    }

    /**
     * Enregistre une nouvelle question en base pour un quiz donné.
     * Utilisé par l'interface admin.
     */
    public void save(Question question) throws SQLException {

        if (question == null) {
            throw new IllegalArgumentException("Question ne doit pas être null");
        }
        if (question.getQuiz() == null) {
            throw new IllegalArgumentException("Question doit avoir un quiz associé (question.getQuiz() == null)");
        }
        if (question.getDifficulte() == null) {
            throw new IllegalArgumentException("Question doit avoir une difficulté associée (question.getDifficulte() == null)");
        }

        String sql =
                "INSERT INTO question " +
                        "  (libelle, choix1, choix2, choix3, choix4, reponse, quiz_id, difficulte_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, question.getLibelle());
            ps.setString(2, question.getChoix1());
            ps.setString(3, question.getChoix2());
            ps.setString(4, question.getChoix3());
            ps.setString(5, question.getChoix4());
            ps.setInt(6, question.getBonneReponse());
            ps.setInt(7, question.getQuiz().getIdQuiz());
            ps.setInt(8, question.getDifficulte().getIdDifficulte());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int generatedId = keys.getInt(1);
                    question.setIdQuestion(generatedId);
                }
            }
        }
    }
}
