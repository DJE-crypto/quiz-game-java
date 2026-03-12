package com.example.quizapp;

import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.dao.ScoreDAO;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Score;
import com.example.quizapp.Session;   // ✅ important

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class QuizController {

    @FXML
    private Label quizTitleLabel;

    @FXML
    private Label progressLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label timerLabel;   // label pour le chrono

    @FXML
    private RadioButton choice1;

    @FXML
    private RadioButton choice2;

    @FXML
    private RadioButton choice3;

    @FXML
    private RadioButton choice4;

    @FXML
    private Button nextButton;

    private ToggleGroup choicesGroup;

    private final QuestionDAO questionDAO = new QuestionDAO();
    private final ScoreDAO scoreDAO = new ScoreDAO();

    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    // ----------- Chronomètre -----------
    private Timeline timer;
    private int remainingSeconds;
    private static final int TIME_PER_QUESTION = 10; // 10 secondes par question
    // -----------------------------------

    @FXML
    public void initialize() {
        // 1) Groupe pour les RadioButtons
        choicesGroup = new ToggleGroup();
        choice1.setToggleGroup(choicesGroup);
        choice2.setToggleGroup(choicesGroup);
        choice3.setToggleGroup(choicesGroup);
        choice4.setToggleGroup(choicesGroup);

        try {
            // 2) Récupérer le quiz courant depuis la Session
            Quiz quiz = Session.getCurrentQuiz();
            if (quiz == null) {
                messageLabel.setText("Aucun quiz sélectionné.");
                nextButton.setDisable(true);
                return;
            }

            quizTitleLabel.setText("Quiz : " + quiz.getTitre());

            // 3) Charger les questions de ce quiz
            questions = questionDAO.findByQuiz(quiz.getIdQuiz());

            if (questions == null || questions.isEmpty()) {
                messageLabel.setStyle("-fx-text-fill: #e67e22;");
                messageLabel.setText("Aucune question pour ce quiz.");
                nextButton.setDisable(true);
                return;
            }

            // 4) Afficher la première question
            showQuestion();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors du chargement du quiz.");
            nextButton.setDisable(true);
        }
    }

    /**
     * Affiche la question courante et démarre le chronomètre.
     */
    private void showQuestion() {
        Question q = questions.get(currentIndex);

        questionLabel.setText(q.getLibelle());
        choice1.setText(q.getChoix1());
        choice2.setText(q.getChoix2());
        choice3.setText(q.getChoix3());
        choice4.setText(q.getChoix4());

        // aucune réponse sélectionnée au départ
        choicesGroup.selectToggle(null);

        progressLabel.setText("Question " + (currentIndex + 1) + " / " + questions.size());
        messageLabel.setText("");

        // démarrer le chrono pour cette question
        startTimer();
    }

    /**
     * Démarre (ou redémarre) le timer à TIME_PER_QUESTION secondes.
     */
    private void startTimer() {
        // Arrêter un éventuel ancien timer
        if (timer != null) {
            timer.stop();
        }

        remainingSeconds = TIME_PER_QUESTION;
        updateTimerLabel();

        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    remainingSeconds--;
                    updateTimerLabel();

                    if (remainingSeconds <= 0) {
                        // Temps écoulé → question perdue, on passe à la suivante
                        timer.stop();
                        handleTimeout();
                    }
                })
        );

        timer.setCycleCount(TIME_PER_QUESTION);
        timer.play();
    }

    /**
     * Met à jour le label "Temps restant : X s".
     */
    private void updateTimerLabel() {
        if (timerLabel != null) {
            timerLabel.setText("Temps restant : " + remainingSeconds + " s");
        }
    }

    /**
     * Appelée automatiquement quand le temps arrive à 0.
     * On considère la question comme fausse et on passe à la suivante.
     */
    private void handleTimeout() {
        // Pas de modification du score → question ratée
        currentIndex++;
        if (currentIndex < questions.size()) {
            showQuestion();
        } else {
            finishQuiz();
        }
    }

    /**
     * Quand l'utilisateur clique sur "Suivant".
     */
    @FXML
    private void handleNext() {
        // arrêter le timer pour cette question
        if (timer != null) {
            timer.stop();
        }

        if (choicesGroup.getSelectedToggle() == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Veuillez choisir une réponse.");
            return;
        }

        int selectedIndex;
        if (choicesGroup.getSelectedToggle() == choice1) selectedIndex = 1;
        else if (choicesGroup.getSelectedToggle() == choice2) selectedIndex = 2;
        else if (choicesGroup.getSelectedToggle() == choice3) selectedIndex = 3;
        else selectedIndex = 4;

        Question q = questions.get(currentIndex);
        if (selectedIndex == q.getBonneReponse()) {
            score++;
        }

        currentIndex++;
        if (currentIndex < questions.size()) {
            showQuestion();
        } else {
            finishQuiz();
        }
    }

    /**
     * Fin du quiz : afficher le score, sauvegarder, ouvrir la page résultat.
     */
    private void finishQuiz() {
        // arrêter le timer si encore actif
        if (timer != null) {
            timer.stop();
        }

        nextButton.setDisable(true);
        int total = questions.size();

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Quiz terminé ! Score : " + score + " / " + total);

        // Sauvegarde score en mémoire (Session) pour la page résultat
        Session.setLastScore(score);
        Session.setLastTotalQuestions(total);

        // Sauvegarde en base (table "score")
        try {
            if (Session.getCurrentUser() != null && Session.getCurrentQuiz() != null) {
                Score s = new Score(
                        0,
                        score,
                        LocalDate.now(),
                        Session.getCurrentUser(),
                        Session.getCurrentQuiz()
                );
                scoreDAO.save(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ouvrir la page result-view.fxml
        try {
            Stage stage = (Stage) nextButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("result-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 600, 400);
            scene.getStylesheets().add(
                    HelloApplication.class.getResource("style.css").toExternalForm()
            );
            stage.setTitle("Résultat du quiz");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // si erreur, on reste sur la page actuelle
        }
    }
}
