package com.example.quizapp;

import com.example.quizapp.dao.UtilisateurDAO;
import com.example.quizapp.model.Utilisateur;

public class TestLogin {

    public static void main(String[] args) {
        UtilisateurDAO dao = new UtilisateurDAO();

        String email = "admin@mail.com";   // adapte à ce que on a  en BD
        String password = "12345";

        try {
            Utilisateur u = dao.login(email, password);

            if (u != null) {
                System.out.println("Login OK !");
                System.out.println("Nom   : " + u.getNom());
                System.out.println("Email : " + u.getEmail());
                System.out.println("Admin : " + (u.isAdmin() ? "OUI" : "NON"));
            } else {
                System.out.println("Login échoué : email ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
