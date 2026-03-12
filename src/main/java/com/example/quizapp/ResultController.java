package com.example.quizapp;

import com.example.quizapp.model.Quiz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private Label quizTitleLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label detailLabel;

    @FXML
    public void initialize() {
        // Récupère les infos depuis la Session
        Quiz quiz = Session.getCurrentQuiz();
        int score = Session.getLastScore();
        int total = Session.getLastTotalQuestions();

        // Titre
        if (quiz != null) {
            quizTitleLabel.setText("Quiz : " + quiz.getTitre());
        } else {
            quizTitleLabel.setText("Quiz");
        }

        // Score brut
        scoreLabel.setText("Votre score : " + score + " / " + total);

        // Pourcentage + commentaire
        double pourcentage = total > 0 ? (score * 100.0 / total) : 0.0;
        String appreciation;
        if (pourcentage >= 80) {
            appreciation = "Excellent !";
        } else if (pourcentage >= 50) {
            appreciation = "Pas mal, continuez !";
        } else {
            appreciation = "Vous pouvez faire mieux, réessayez 🙂";
        }

        detailLabel.setText(String.format("Soit %.1f %% - %s", pourcentage, appreciation));
    }

    @FXML
    private void handleBackToMenu() {
        try {
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);

            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );

            stage.setTitle("Menu des Quiz");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) scoreLabel.getScene().getWindow();
        stage.close();
    }
}
