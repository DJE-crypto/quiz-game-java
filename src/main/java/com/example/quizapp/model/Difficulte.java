package com.example.quizapp.model;

public class Difficulte {

    private int idDifficulte;
    private String niveau; // Facile / Moyen / Difficile

    public Difficulte() {
    }

    public Difficulte(int idDifficulte, String niveau) {
        this.idDifficulte = idDifficulte;
        this.niveau = niveau;
    }

    public int getIdDifficulte() {
        return idDifficulte;
    }

    public void setIdDifficulte(int idDifficulte) {
        this.idDifficulte = idDifficulte;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return niveau;
    }
}
