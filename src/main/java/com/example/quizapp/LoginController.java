package com.example.quizapp;

import com.example.quizapp.dao.UtilisateurDAO;
import com.example.quizapp.model.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginButton;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    public void initialize() {
        // Entrée dans les champs => login
        passwordField.setOnAction(e -> handleLogin());
        emailField.setOnAction(e -> handleLogin());
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String mdp   = passwordField.getText();

        if (email.isEmpty() || mdp.isEmpty()) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Veuillez entrer l'email et le mot de passe.");
            return;
        }

        try {
            Utilisateur u = utilisateurDAO.login(email, mdp);

            if (u == null) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Email ou mot de passe incorrect.");
                return;
            }

            // Mémoriser l'utilisateur
            Session.setCurrentUser(u);

            // Ouvrir le menu
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            // CSS global
            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );

            stage.setTitle("Menu des Quiz");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors de la connexion.");
        }
    }

    // Lien / bouton "Créer un compte"
    @FXML
    private void handleOpenRegister() {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 450);

            // CSS global
            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );

            stage.setTitle("Créer un compte");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors de l'ouverture de la page d'inscription.");
        }
    }
}
