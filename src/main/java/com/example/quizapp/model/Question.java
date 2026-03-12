package com.example.quizapp.model;

public class Question {

    private int idQuestion;
    private String libelle;
    private String choix1;
    private String choix2;
    private String choix3;
    private String choix4;
    private int bonneReponse; // 1, 2, 3 ou 4
    private Quiz quiz;
    private Difficulte difficulte;

    public Question() {
    }

    public Question(int idQuestion, String libelle,
                    String choix1, String choix2, String choix3, String choix4,
                    int bonneReponse, Quiz quiz, Difficulte difficulte) {
        this.idQuestion = idQuestion;
        this.libelle = libelle;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.choix4 = choix4;
        this.bonneReponse = bonneReponse;
        this.quiz = quiz;
        this.difficulte = difficulte;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getChoix1() {
        return choix1;
    }

    public void setChoix1(String choix1) {
        this.choix1 = choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public void setChoix2(String choix2) {
        this.choix2 = choix2;
    }

    public String getChoix3() {
        return choix3;
    }

    public void setChoix3(String choix3) {
        this.choix3 = choix3;
    }

    public String getChoix4() {
        return choix4;
    }

    public void setChoix4(String choix4) {
        this.choix4 = choix4;
    }

    public int getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(int bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Difficulte difficulte) {
        this.difficulte = difficulte;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
