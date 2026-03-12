package com.example.quizapp;

import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.dao.ThemeDAO;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Theme;
import com.example.quizapp.model.Utilisateur;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class MenuController {
    @FXML
    private Button deleteQuizButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ComboBox<Theme> themeComboBox;

    @FXML
    private ListView<Quiz> quizListView;

    @FXML
    private Button startQuizButton;

    @FXML
    private Button createQuizButton;

    @FXML
    private Label messageLabel;

    private final ThemeDAO themeDAO = new ThemeDAO();
    private final QuizDAO quizDAO = new QuizDAO();

    @FXML
    public void initialize() {
        // 1) Récupérer l'utilisateur connecté depuis la Session
        Utilisateur user = Session.getCurrentUser();

        if (user != null) {
            // Message de bienvenue
            if (user.isAdmin()) {
                welcomeLabel.setText("Bienvenue, " + user.getNom() + " (Administrateur)");
            } else {
                welcomeLabel.setText("Bienvenue, " + user.getNom());
            }
        } else {
            welcomeLabel.setText("Bienvenue dans l'application de quiz");
        }

        // 2) Cacher les boutons admin pour les simples utilisateurs
        boolean isAdmin = (user != null && user.isAdmin());

        if (!isAdmin) {
            // l’utilisateur simple ne voit pas ces boutons
            createQuizButton.setVisible(false);
            createQuizButton.setManaged(false);

            deleteQuizButton.setVisible(false);
            deleteQuizButton.setManaged(false);
        } else {
            // admin : boutons visibles
            createQuizButton.setVisible(true);
            createQuizButton.setManaged(true);

            deleteQuizButton.setVisible(true);
            deleteQuizButton.setManaged(true);
        }

        // 3) Charger les thèmes dans la ComboBox
        try {
            themeComboBox.setItems(FXCollections.observableArrayList(
                    themeDAO.findAll()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors du chargement des thèmes.");
        }

        // 4) Quand on change de thème, recharger les quiz
        themeComboBox.setOnAction(event -> {
            Theme selectedTheme = themeComboBox.getValue();
            if (selectedTheme != null) {
                loadQuizzesForTheme(selectedTheme);
            } else {
                quizListView.getItems().clear();
            }
        });

        // 5) Activer / désactiver les boutons selon la sélection
        startQuizButton.setDisable(true);
        if (isAdmin) {
            deleteQuizButton.setDisable(true);
        }

        quizListView.getSelectionModel().selectedItemProperty().addListener((obs, oldQuiz, newQuiz) -> {
            startQuizButton.setDisable(newQuiz == null);
            if (isAdmin) {
                deleteQuizButton.setDisable(newQuiz == null);
            }
            messageLabel.setText("");
        });
    }


    // Charger les quiz d'un thème
    private void loadQuizzesForTheme(Theme theme) {
        try {
            List<Quiz> quizzes = quizDAO.findByTheme(theme.getIdTheme());
            quizListView.setItems(FXCollections.observableArrayList(quizzes));
            messageLabel.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors du chargement des quiz.");
        }
    }

    // Bouton "Commencer le quiz"
    @FXML
    private void handleStartQuiz() {
        Quiz selectedQuiz = quizListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz == null) {
            messageLabel.setText("Veuillez sélectionner un quiz.");
            return;
        }

        // Sauvegarder le quiz choisi dans la Session
        Session.setCurrentQuiz(selectedQuiz);

        // Ouvrir la fenêtre du quiz
        try {
            Stage stage = (Stage) startQuizButton.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );
            stage.setTitle("Quiz");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de l'ouverture du quiz.");
        }
    }

    @FXML
    private void handleDeleteQuiz() {
        Quiz selectedQuiz = quizListView.getSelectionModel().getSelectedItem();

        if (selectedQuiz == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Veuillez sélectionner un quiz à supprimer.");
            return;
        }

        // Boîte de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer le quiz");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer le quiz \""
                + selectedQuiz.getTitre() + "\" ?");

        ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(oui, non);

        var result = alert.showAndWait();
        if (result.isEmpty() || result.get() == non) {
            return; // l’utilisateur a annulé
        }

        // Suppression en base
        try {
            quizDAO.delete(selectedQuiz.getIdQuiz());

            // recharger la liste pour le thème courant
            Theme currentTheme = themeComboBox.getValue();
            if (currentTheme != null) {
                loadQuizzesForTheme(currentTheme);
            } else {
                quizListView.getItems().clear();
            }

            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Quiz supprimé avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors de la suppression du quiz.");
        }
    }
    // Bouton "Créer un quiz" (réservé à l’admin)
    @FXML
    private void handleCreateQuiz() {
        // Vérifier que l'utilisateur est admin (sécurité côté Java)
        Utilisateur user = Session.getCurrentUser();
        if (user == null || !user.isAdmin()) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Seul l'administrateur peut créer des quiz.");
            return;
        }

        try {
            Stage stage = (Stage) createQuizButton.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(HelloApplication.class.getResource("admin-quiz-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );
            stage.setTitle("Création d'un quiz");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors de l'ouverture de la page admin.");
        }
    }
}
