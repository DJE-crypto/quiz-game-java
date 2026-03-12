package com.example.quizapp.model;

import java.time.LocalDate;

public class Score {

    private int idScore;
    private int valeur;
    private LocalDate dateScore;
    private Utilisateur utilisateur;
    private Quiz quiz;

    public Score() {
    }

    public Score(int idScore, int valeur, LocalDate dateScore,
                 Utilisateur utilisateur, Quiz quiz) {
        this.idScore = idScore;
        this.valeur = valeur;
        this.dateScore = dateScore;
        this.utilisateur = utilisateur;
        this.quiz = quiz;
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public LocalDate getDateScore() {
        return dateScore;
    }

    public void setDateScore(LocalDate dateScore) {
        this.dateScore = dateScore;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Score{" +
                "valeur=" + valeur +
                ", dateScore=" + dateScore +
                '}';
    }
}
