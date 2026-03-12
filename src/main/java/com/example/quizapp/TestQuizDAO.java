package com.example.quizapp;

import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.model.Quiz;

import java.util.List;

public class TestQuizDAO {
    public static void main(String[] args) {
        try {
            QuizDAO dao = new QuizDAO();

            // mets ici l'id_theme du thème "Géographie" (regarde dans la table theme)
            int themeId = 1;

            List<Quiz> quizzes = dao.findByTheme(themeId);

            System.out.println("Nombre de quiz trouvés = " + quizzes.size());
            for (Quiz q : quizzes) {
                System.out.println(q.getIdQuiz() + " | " + q.getTitre());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
