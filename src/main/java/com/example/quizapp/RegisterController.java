package com.example.quizapp;

import com.example.quizapp.dao.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    private void handleRegister() {
        String nom     = nameField.getText().trim();
        String email   = emailField.getText().trim();
        String mdp     = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || confirm.isEmpty()) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        if (!mdp.equals(confirm)) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Les mots de passe ne correspondent pas.");
            return;
        }

        try {
            if (utilisateurDAO.emailExists(email)) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Cet email est déjà utilisé.");
                return;
            }

            // Enregistrer l'utilisateur (role_id = 2 dans UtilisateurDAO.register)
            utilisateurDAO.register(nom, email, mdp);

            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Compte créé avec succès ! Retour à la connexion...");

            // Retour à la page de login
            Stage stage = (Stage) messageLabel.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            // CSS global
            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );

            stage.setTitle("Quiz - Connexion");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors de la création du compte.");
        }
    }

    // Lien "Déjà un compte ? Se connecter"
    @FXML
    private void handleBackToLogin() {
        try {
            Stage stage = (Stage) nameField.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            // CSS global
            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );

            stage.setTitle("Quiz - Connexion");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors du retour à la connexion.");
        }
    }
}
