package com.example.quizapp;

import com.example.quizapp.dao.DifficulteDAO;
import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.dao.ThemeDAO;
import com.example.quizapp.model.Difficulte;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Theme;
import com.example.quizapp.Session;   // ✅ important

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class AdminQuizController {

    // Champs du haut (titre + thème)
    @FXML
    private TextField quizTitleField;

    @FXML
    private ComboBox<Theme> themeComboBox;

    // Question + choix
    @FXML
    private TextArea questionField;

    @FXML
    private TextField choice1Field;

    @FXML
    private TextField choice2Field;

    @FXML
    private TextField choice3Field;

    @FXML
    private TextField choice4Field;

    // Boutons radio pour la bonne réponse
    @FXML
    private RadioButton rbCorrect1;

    @FXML
    private RadioButton rbCorrect2;

    @FXML
    private RadioButton rbCorrect3;

    @FXML
    private RadioButton rbCorrect4;

    // Boutons radio pour la difficulté
    @FXML
    private RadioButton rbEasy;

    @FXML
    private RadioButton rbMedium;

    @FXML
    private RadioButton rbHard;

    @FXML
    private Label messageLabel;

    // Groups pour mutualisation des radio buttons
    private ToggleGroup correctAnswerGroup;
    private ToggleGroup difficultyGroup;

    // DAOs
    private final ThemeDAO themeDAO = new ThemeDAO();
    private final DifficulteDAO difficulteDAO = new DifficulteDAO();
    private final QuizDAO quizDAO = new QuizDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();

    // liste des difficultés chargées depuis la BDD
    private List<Difficulte> difficulties;

    // quiz courant (créé une fois)
    private Quiz currentQuiz;

    @FXML
    public void initialize() {
        // Groupe pour la bonne réponse (1,2,3,4)
        correctAnswerGroup = new ToggleGroup();
        rbCorrect1.setToggleGroup(correctAnswerGroup);
        rbCorrect2.setToggleGroup(correctAnswerGroup);
        rbCorrect3.setToggleGroup(correctAnswerGroup);
        rbCorrect4.setToggleGroup(correctAnswerGroup);

        // Groupe pour la difficulté (Facile, Moyenne, Difficile)
        difficultyGroup = new ToggleGroup();
        rbEasy.setToggleGroup(difficultyGroup);
        rbMedium.setToggleGroup(difficultyGroup);
        rbHard.setToggleGroup(difficultyGroup);

        // Charger les thèmes
        try {
            List<Theme> themes = themeDAO.findAll();
            themeComboBox.setItems(FXCollections.observableArrayList(themes));
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors du chargement des thèmes.");
        }

        // Charger les difficultés depuis la BDD
        try {
            difficulties = difficulteDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors du chargement des difficultés.");
        }
    }

    /**
     * Bouton "Enregistrer le quiz" :
     *  ➜ crée le quiz (une seule fois), SANS obliger à saisir une question.
     */
    @FXML
    private void handleSaveQuiz() {
        try {
            String titre = quizTitleField.getText().trim();
            Theme theme = themeComboBox.getValue();

            if (titre.isEmpty() || theme == null) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Veuillez remplir le titre et choisir un thème.");
                return;
            }

            if (Session.getCurrentUser() == null) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Aucun utilisateur connecté.");
                return;
            }

            // Si le quiz n'est pas encore créé : on le crée
            if (currentQuiz == null) {
                Quiz quiz = new Quiz();
                quiz.setTitre(titre);
                quiz.setDateCreation(LocalDate.now());
                quiz.setCreateur(Session.getCurrentUser());
                quiz.setTheme(theme);

                quizDAO.save(quiz);   // remplit id_quiz
                currentQuiz = quiz;

                // on garde aussi dans la Session si on veut le réutiliser
                Session.setCurrentQuiz(quiz);

                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("Quiz créé ! Vous pouvez maintenant ajouter des questions.");
            } else {
                // Quiz déjà créé : éventuellement on pourrait mettre à jour titre/thème
                messageLabel.setStyle("-fx-text-fill: #e67e22;");
                messageLabel.setText("Ce quiz est déjà créé. Ajoutez des questions ou retournez au menu.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors de l'enregistrement du quiz.");
        }
    }

    /**
     * Bouton "Ajouter la question" :
     * - ajoute une question supplémentaire au quiz déjà créé.
     */
    @FXML
    private void handleAddQuestion() {
        try {
            if (currentQuiz == null) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Veuillez d'abord enregistrer le quiz.");
                return;
            }

            if (!saveQuestionForCurrentQuiz()) {
                return;
            }

            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Question ajoutée avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors de l'ajout de la question.");
        }
    }

    /**
     * Fonction utilitaire : sauvegarde UNE question pour currentQuiz.
     * @return true si OK, false si données invalides.
     */
    private boolean saveQuestionForCurrentQuiz() throws Exception {
        if (currentQuiz == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Aucun quiz sélectionné.");
            return false;
        }

        String libelle = questionField.getText().trim();
        String c1 = choice1Field.getText().trim();
        String c2 = choice2Field.getText().trim();
        String c3 = choice3Field.getText().trim();
        String c4 = choice4Field.getText().trim();

        if (libelle.isEmpty() || c1.isEmpty() || c2.isEmpty()
                || c3.isEmpty() || c4.isEmpty()
                || correctAnswerGroup.getSelectedToggle() == null
                || difficultyGroup.getSelectedToggle() == null) {

            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Complétez la question, les 4 choix, la bonne réponse et la difficulté.");
            return false;
        }

        int bonneReponse;
        if (correctAnswerGroup.getSelectedToggle() == rbCorrect1) bonneReponse = 1;
        else if (correctAnswerGroup.getSelectedToggle() == rbCorrect2) bonneReponse = 2;
        else if (correctAnswerGroup.getSelectedToggle() == rbCorrect3) bonneReponse = 3;
        else bonneReponse = 4;

        // Récupérer l'objet Difficulte correspondant au radio sélectionné
        Difficulte diff = getSelectedDifficulty();
        if (diff == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Difficulté non trouvée en base. Vérifiez la table 'difficulte'.");
            return false;
        }

        Question q = new Question();
        q.setLibelle(libelle);
        q.setChoix1(c1);
        q.setChoix2(c2);
        q.setChoix3(c3);
        q.setChoix4(c4);
        q.setBonneReponse(bonneReponse);
        q.setQuiz(currentQuiz);
        q.setDifficulte(diff);

        questionDAO.save(q);

        // vider les champs de la question pour en saisir une autre
        questionField.clear();
        choice1Field.clear();
        choice2Field.clear();
        choice3Field.clear();
        choice4Field.clear();
        correctAnswerGroup.selectToggle(null);
        // on laisse la difficulté cochée (pratique si on enchaîne plusieurs questions de même niveau)

        return true;
    }

    /**
     * Retourne l'objet Difficulte (BDD) correspondant au bouton radio sélectionné.
     * On compare par le texte ("Facile", "Moyenne", "Difficile").
     */
    private Difficulte getSelectedDifficulty() {
        if (difficulties == null || difficulties.isEmpty()) {
            return null;
        }

        String label;
        if (difficultyGroup.getSelectedToggle() == rbEasy) {
            label = "Facile";
        } else if (difficultyGroup.getSelectedToggle() == rbMedium) {
            label = "Moyenne";
        } else {
            label = "Difficile";
        }

        for (Difficulte d : difficulties) {
            if (d.getNiveau() != null &&
                    d.getNiveau().equalsIgnoreCase(label)) {
                return d;
            }
        }
        return null;
    }

    // 🔙 Bouton "Retour au menu"
    @FXML
    private void handleBackToMenu() {
        try {
            Stage stage = (Stage) quizTitleField.getScene().getWindow();
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
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Erreur lors du retour au menu.");
        }
    }
}
