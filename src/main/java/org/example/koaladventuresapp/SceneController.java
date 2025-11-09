package org.example.koaladventuresapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private static int currentScore = 0;

    @FXML
    private Label titleLabel;

    @FXML
    private Label subTitleLabel;

    @FXML
    private ImageView myHomeBackground;

    @FXML
    private ImageView myInstructionId;

    @FXML
    private AnchorPane tit;

    @FXML
    private AnchorPane instructionPane;

    @FXML
    private Region space1;

    @FXML
    private Region space2;

    @FXML
    public void initialize() {
        if (myHomeBackground != null && tit != null) {
            myHomeBackground.fitWidthProperty().bind(tit.widthProperty());
            myHomeBackground.fitHeightProperty().bind(tit.heightProperty());
            myHomeBackground.setPreserveRatio(false);
        }

        if (myInstructionId != null && instructionPane != null) {
            myInstructionId.fitWidthProperty().bind(instructionPane.widthProperty());
            myInstructionId.fitHeightProperty().bind(instructionPane.heightProperty());
            myInstructionId.setPreserveRatio(false);

            //VBox.setVgrow(space1, Priority.ALWAYS);
            //VBox.setVgrow(space2, Priority.ALWAYS);
        }
    }

    public void switchToSceneHome (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("homescreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        boolean wasFullScreen = stage.isFullScreen();

        scene = new Scene(root);
        stage.setScene(scene);

        if (wasFullScreen) {
            stage.setFullScreen(true);
        }

        String css = getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.show();
    }

    public void switchToScene2 (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String css = getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public void switchToInstructions (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("instructions.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        boolean wasFullScreen = stage.isFullScreen();

        scene = new Scene(root);

        String css = getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);

        if (wasFullScreen) {
            stage.setFullScreen(true);
        }

        stage.show();
    }

    public void showLeaderboard (ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leaderboard");
        alert.setHeaderText("Current Score");
        alert.setContentText("Your Score is: " + currentScore +"  points");
        alert.initOwner(((Node)event.getSource()).getScene().getWindow());
        alert.showAndWait();
    }

    public static void addToScore(int points) {
        currentScore += points;
    }

    public static int getScore() {
        return currentScore;
    }


    // When you add your methods, uncomment and put your scene in here
    /*
    public void switchToAnotherScene (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("anotherscene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

     */
}
