package org.example.koaladventuresapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TreeController {
    @FXML
    private ImageView treeImage;

    @FXML
    private ImageView choppedTreeImage;

    @FXML
    private ImageView koalaImage;

    private Stage currentStage;

    @FXML
    private Label statusLabel;

    @FXML
    private Label instructionsLabel;

    @FXML
    private Button chopButton;

    @FXML
    private Label timerLabel;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane anchorPane;

    private int chopsLeft = 3;
    private int timeLeft = 10;
    private Timeline timeline;

    @FXML
    private void initialize() {
        treeImage.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/tree_full.png")));
        treeImage.fitWidthProperty().bind(rootPane.widthProperty().subtract(100));
        treeImage.fitHeightProperty().bind(rootPane.heightProperty().subtract(100));
        treeImage.setPreserveRatio(true);

        choppedTreeImage.setImage(null);
        choppedTreeImage.fitWidthProperty().bind(rootPane.widthProperty().subtract(100));
        choppedTreeImage.fitHeightProperty().bind(rootPane.heightProperty().subtract(100));

        koalaImage.setImage(null);
        koalaImage.fitWidthProperty().bind(rootPane.widthProperty().multiply(0.4));
        koalaImage.fitHeightProperty().bind(rootPane.widthProperty().multiply(0.4));

        chopButton.setDisable(false);

        statusLabel.setText("chops left: " + chopsLeft);
        instructionsLabel.setText("Keep the koala safe! Don't chop the tree down!");

        timerLabel.setText("time: " + timeLeft);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            timerLabel.setText("time: " + timeLeft);
            if (timeLeft <= 0) {
                timeline.stop();
                chopButton.setDisable(true);
                statusLabel.setText("Koala is safe!");
                showEndScreen();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void chopTree() {
        chopsLeft--;
        if (chopsLeft > 0) {
            statusLabel.setText("chops left: " + chopsLeft);
            updateTreeImage();
        }
        else {
            statusLabel.setText("");
            treeImage.setImage(null);
            choppedTreeImage.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/tree_stump.png")));
            koalaImage.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/koala.png")));

            chopButton.setDisable(true);
            if (timeline != null) {
                timeline.stop();
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Tree Chopped :(");
            dialog.setHeaderText("You chopped the tree down, and now the koala doesn't have a home.");
            dialog.setContentText("Deforestation is one of the biggest threats to koalas, destroying the trees they depend on for food and shelter. When trees are cut down for development, koalas lose their homes and are forced to travel across dangerous roads in search of safety. Protecting forests means protecting koalas. Would you like to try again?");

            DialogPane pane = dialog.getDialogPane();
            pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    resetGame();
                }
            });
        }
    }

    public void resetGame() {
        chopsLeft = 3;
        timeLeft = 10;

        treeImage.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/tree_full.png")));
        choppedTreeImage.setImage(null);
        koalaImage.setImage(null);
        chopButton.setDisable(false);

        statusLabel.setText("chops left: " + chopsLeft);
        instructionsLabel.setText("Keep the koala safe! Don't chop the tree down!");

        if (timeline != null) {
            timeline.stop();
        }
        timerLabel.setText("time: " + timeLeft);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            timerLabel.setText("time: " + timeLeft);
            if (timeLeft <= 0) {
                timeline.stop();
                chopButton.setDisable(true);
                statusLabel.setText("Koala is safe!");
                showEndScreen();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void updateTreeImage() {
        switch (chopsLeft) {
            case 2 -> treeImage.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/tree_stage1.png")));
            case 1 -> treeImage.setImage(new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/tree_stage2.png")));
        }
    }

    private void showEndScreen() {
        Image endImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/victory.gif"));
        ImageView endView = new ImageView(endImage);
        endView.setFitWidth(250);
        endView.setPreserveRatio(true);

        Button backToMapButton = new Button("Back to Map");
        backToMapButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px;");
        backToMapButton.setOnAction(event -> goBackToMap());

        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: pink;");
        vbox.getChildren().addAll(endView, backToMapButton);

        Scene endScene = new Scene(vbox, 600, 400);

        currentStage = (Stage) chopButton.getScene().getWindow();
        currentStage.setScene(endScene);
    }

    private void goBackToMap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("map.fxml"));
            Parent mapRoot = loader.load();
            Scene mapScene = new Scene(mapRoot);

            boolean wasFullScreen = currentStage.isFullScreen();
            currentStage.setScene(mapScene);

            if (wasFullScreen) {
                currentStage.setFullScreen(true);
            }
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
