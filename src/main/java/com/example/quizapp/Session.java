package com.example.quizapp;

import com.example.quizapp.model.Utilisateur;
import com.example.quizapp.model.Quiz;

public class Session {

    private static Utilisateur currentUser;
    private static Quiz currentQuiz;
    private static int lastScore;
    private static int lastTotalQuestions;

    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Utilisateur user) {
        currentUser = user;
    }

    public static boolean isCurrentUserAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    public static Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public static void setCurrentQuiz(Quiz quiz) {
        currentQuiz = quiz;
    }

    public static int getLastScore() {
        return lastScore;
    }

    public static void setLastScore(int score) {
        lastScore = score;
    }

    public static int getLastTotalQuestions() {
        return lastTotalQuestions;
    }

    public static void setLastTotalQuestions(int total) {
        lastTotalQuestions = total;
    }

    public static void clear() {
        currentUser = null;
        currentQuiz = null;
        lastScore = 0;
        lastTotalQuestions = 0;
    }
}
