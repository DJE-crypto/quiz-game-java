package com.example.quizapp.model;

import java.time.LocalDate;

public class Quiz {

    private int idQuiz;
    private String titre;
    private LocalDate dateCreation;
    private Utilisateur createur;
    private Theme theme;

    public Quiz() {
    }

    public Quiz(int idQuiz, String titre, LocalDate dateCreation,
                Utilisateur createur, Theme theme) {
        this.idQuiz = idQuiz;
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.createur = createur;
        this.theme = theme;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return titre;
    }
}
