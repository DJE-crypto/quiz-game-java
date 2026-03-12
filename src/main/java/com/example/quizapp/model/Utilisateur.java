package com.example.quizapp.model;

public class Utilisateur {

    private int idUtilisateur;
    private String nom;
    private String email;
    private String motDePasse;
    private boolean admin;   // true = administrateur, false = utilisateur simple

    // --- Constructeurs ---

    public Utilisateur() {
    }

    public Utilisateur(int idUtilisateur, String nom, String email,
                       String motDePasse, boolean admin) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.admin = admin;
    }

    // --- Getters / Setters ---

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return nom + " (" + email + ")";
    }
}
