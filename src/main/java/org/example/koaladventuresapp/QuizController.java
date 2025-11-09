package org.example.koaladventuresapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QuizController {

    @FXML private Label questionLabel;
    @FXML private Label feedbackLabel;
    @FXML private Label scoreLabel;
    @FXML private Label infoLabel;
    @FXML private Button choiceA, choiceB, choiceC, choiceD;
    @FXML private Button nextButton;
    @FXML private Button backButton;
    @FXML private ImageView koalaGif;
    @FXML private ImageView victoryGif;

    private int currentQuestion = 0;
    private int score = 0;

    private final String[][] questions = {
            {"Why are koalas threatened with extinction?", "Habitat loss", "Pollution", "Competition with kangaroos", "Overfishing", "Habitat loss"},
            {"Which tree do koalas depend on for food?", "Birch", "Eucalyptus", "Oak", "Pine", "Eucalyptus"},
            {"How many hours do koalas sleep/day?", "6", "12", "18-22", "24", "18-22"}
    };

    @FXML
    public void initialize() {
        victoryGif.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/victory.gif")));
        koalaGif.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/koala.gif")));

        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText(questions[currentQuestion][0]);
            choiceA.setText(questions[currentQuestion][1]);
            choiceB.setText(questions[currentQuestion][2]);
            choiceC.setText(questions[currentQuestion][3]);
            choiceD.setText(questions[currentQuestion][4]);

            feedbackLabel.setText("");
            feedbackLabel.setStyle("-fx-text-fill: #6a1b3f; -fx-font-size: 14px;");

            hideScoreAndVictory();

            choiceA.setVisible(true);
            choiceB.setVisible(true);
            choiceC.setVisible(true);
            choiceD.setVisible(true);

            choiceA.setDisable(false);
            choiceB.setDisable(false);
            choiceC.setDisable(false);
            choiceD.setDisable(false);

            nextButton.setDisable(true);

        } else {
            questionLabel.setText("🎉 Quiz complete!");
            showScoreAndVictory();
            choiceA.setVisible(false);
            choiceB.setVisible(false);
            choiceC.setVisible(false);
            choiceD.setVisible(false);
            nextButton.setDisable(true);
        }
    }

    private void hideScoreAndVictory() {
        if (scoreLabel != null) {
            scoreLabel.setVisible(false);
            scoreLabel.setManaged(false);
        }
        if (victoryGif != null) {
            victoryGif.setVisible(false);
            victoryGif.setManaged(false);
        }
        if (infoLabel != null) {
            infoLabel.setVisible(false);
        }
    }

    private void showScoreAndVictory() {
        if (scoreLabel != null) {
            scoreLabel.setText("Your score: " + score + " / " + questions.length);
            scoreLabel.setVisible(true);
            scoreLabel.setManaged(true);
        }
        if (victoryGif != null) {
            victoryGif.setVisible(true);
            victoryGif.setManaged(true);
        }
        if (infoLabel != null) {
            infoLabel.setText(
                    "Koalas are in danger because their eucalyptus forests are being cleared and damaged.\n" +
                            "You can help by planting trees, supporting wildlife groups, and driving carefully near koala habitats."
            );
            infoLabel.setVisible(true);
        }
    }

    public void chooseA(ActionEvent e) { answer(questions[currentQuestion][1]); }
    public void chooseB(ActionEvent e) { answer(questions[currentQuestion][2]); }
    public void chooseC(ActionEvent e) { answer(questions[currentQuestion][3]); }
    public void chooseD(ActionEvent e) { answer(questions[currentQuestion][4]); }

    private void answer(String selected) {
        if (!nextButton.isDisable()) {
            return;
        }

        String correct = questions[currentQuestion][5];

        if (selected.equals(correct)) {
            score++;
            feedbackLabel.setText("Correct");
            feedbackLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 14px;");
        } else {
            feedbackLabel.setText("Incorrect");
            feedbackLabel.setStyle("-fx-text-fill: #c62828; -fx-font-size: 14px;");
        }

        choiceA.setDisable(true);
        choiceB.setDisable(true);
        choiceC.setDisable(true);
        choiceD.setDisable(true);
        nextButton.setDisable(false);
    }

    @FXML
    public void nextQuestion(ActionEvent event) {
        if (currentQuestion < questions.length) {
            currentQuestion++;
            loadQuestion();
        }
    }

    @FXML
    public void restartQuiz(ActionEvent event) {
        currentQuestion = 0;
        score = 0;

        choiceA.setVisible(true);
        choiceB.setVisible(true);
        choiceC.setVisible(true);
        choiceD.setVisible(true);

        loadQuestion();
    }
}

