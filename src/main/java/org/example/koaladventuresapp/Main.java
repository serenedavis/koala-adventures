package org.example.koaladventuresapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("homescreen.fxml"));
            Scene scene = new Scene(root);

            String css = getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Koala Adventures");
            primaryStage.setWidth(600);
            primaryStage.setHeight(400);

            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("Press Esc to exit");

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
