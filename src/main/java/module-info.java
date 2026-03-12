module com.example.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    // on ajoute jBCrypt
    requires  jbcrypt;   // <- important

    // pour FXML
    opens com.example.quizapp to javafx.fxml;
    opens com.example.quizapp.dao to javafx.fxml;
    opens com.example.quizapp.model to javafx.base;

    exports com.example.quizapp;
}
