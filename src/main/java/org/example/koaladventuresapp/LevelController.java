package org.example.koaladventuresapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelController {
    @FXML
    public void loadLevel1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tree-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        boolean wasFullScreen = stage.isFullScreen();
        stage.setScene(scene);

        if(wasFullScreen) {
            stage.setFullScreen(true);
        }
        stage.show();
    }

    @FXML
    public void loadLevel2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        boolean wasFullScreen = stage.isFullScreen();
        stage.setScene(scene);

        if(wasFullScreen) {
            stage.setFullScreen(true);
        }
        stage.show();
    }

    @FXML
    public void goHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("homescreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        String css = getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);

        if(stage.isFullScreen()) {
            stage.setFullScreen(true);
        }

        stage.show();
    }

    @FXML
    public void loadQuiz(ActionEvent event) throws IOException {
        try {
            System.out.println("loadQuiz called!");
            Parent root = FXMLLoader.load(getClass().getResource("quiz.fxml"));
            System.out.println("Quiz FXML loaded");

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            System.out.println("Stage obtained");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            if(stage.isFullScreen()) {
                stage.setFullScreen(true);
            }
            stage.show();
            System.out.println("Quiz scene shown");
        } catch (Exception e) {
            System.err.println("Error loading quiz: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
