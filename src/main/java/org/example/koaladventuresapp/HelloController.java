package org.example.koaladventuresapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class HelloController {

    @FXML
    private VBox rootBox;

    @FXML
    private Label titleLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label healthLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Button startButton;

    private static final int ROWS = 7;
    private static final int COLS = 9;

    private final char[][] map = new char[ROWS][COLS];
    private final Label[][] tiles = new Label[ROWS][COLS];

    private int playerRow;
    private int playerCol;
    private int currentLevel;
    private int koalasFound;
    private boolean carryingKoala;
    private static final int TOTAL_KOALAS = 4;

    private int health;
    private boolean gameOver;
    private boolean justHitFire;

    private Image treeImage;
    private Image fireImage;
    private Image rescuerImage;
    private Image rescuerKoalaImage;
    private Image rescuerFireImage;
    private Image deadKoalaImage;
    private Image victoryImage;
    private Image rescueCenterImage;

    @FXML
    private void initialize() {
        rootBox.setOnKeyPressed(event -> handleKey(event.getCode()));

        treeImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/tree_fire_2.png"));
        fireImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/tree_fire_1.png"));

        rescuerImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/rescuer_with_koala.png"));
        rescuerKoalaImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/rescuer.png"));

        rescuerFireImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/rescuer_fire.png"));
        deadKoalaImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/dead_koala.png"));
        victoryImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/VictoryAnimation.gif"));
        rescueCenterImage = new Image(getClass().getResourceAsStream("/org/example/koaladventuresapp/rescue_center.png"));
    }


    @FXML
    protected void onStartClick() {
        startButton.setVisible(false);
        startButton.setManaged(false);

        koalasFound = 0;
        currentLevel = 1;
        carryingKoala = false;
        gameOver = false;
        justHitFire = false;
        health = 3;

        infoLabel.setStyle("-fx-font-size: 10px;");
        statusLabel.setStyle("-fx-font-size: 12px;");
        updateHealthLabel();

        createGrid();
        buildLevel();
        System.out.println("After buildLevel, carryingKoala = " + carryingKoala);
        redrawGrid();

        titleLabel.setText("Find and Rescue the Koalas");
        infoLabel.setText("Use the arrow keys to find each koala and bring it to the rescue center.");
        statusLabel.setText("Level 1: Find the koala, then bring it to the rescue center (C).");

        rootBox.requestFocus();
    }

    private void updateHealthLabel() {
        String hearts = switch (health) {
            case 3 -> "❤️❤️❤️";
            case 2 -> "❤️❤️🤍";
            case 1 -> "❤️🤍🤍";
            default -> "🤍🤍🤍";
        };
        healthLabel.setText("Health: " + hearts);
    }

    private void createGrid() {
        gameGrid.getChildren().clear();
        gameGrid.setHgap(1);
        gameGrid.setVgap(1);
        gameGrid.setAlignment(Pos.CENTER);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Label tile = new Label();
                tile.setMinSize(30, 30);
                tile.setMaxSize(30, 30);
                tile.setAlignment(Pos.CENTER);
                tiles[r][c] = tile;
                gameGrid.add(tile, c, r);
            }
        }
    }

    private void buildLevel() {
        justHitFire = false;

        String[] layout;

        switch (currentLevel) {
            case 1 -> layout = new String[]{
                    "TTTTTTTTT",
                    "T.....C.T",
                    "T..F....T",
                    "T...P...T",
                    "T....K..T",
                    "T.......T",
                    "TTTTTTTTT"
            };
            case 2 -> layout = new String[]{
                    "TTTTTTTTT",
                    "T.F...C.T",
                    "T.......T",
                    "T..P....T",
                    "T.....K.T",
                    "T.......T",
                    "TTTTTTTTT"
            };
            case 3 -> layout = new String[]{
                    "TTTTTTTTT",
                    "T...F..CT",
                    "T.......T",
                    "T.P.F...T",
                    "T..F.K..T",
                    "T.......T",
                    "TTTTTTTTT"
            };
            case 4 -> layout = new String[]{
                    "TTTTTTTTT",
                    "T.F...F.T",
                    "T....C..T",
                    "T..P....T",
                    "T...F.K.T",
                    "T.......T",
                    "TTTTTTTTT"
            };
            default -> layout = new String[]{
                    "TTTTTTTTT",
                    "T.......T",
                    "T...P...T",
                    "T.......T",
                    "T.......T",
                    "T.......T",
                    "TTTTTTTTT"
            };
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                char ch = layout[r].charAt(c);
                if (ch == 'P') {
                    playerRow = r;
                    playerCol = c;
                    map[r][c] = '.';
                } else {
                    map[r][c] = ch;
                }
            }
        }

        statusLabel.setText("Level " + currentLevel + " – Koalas rescued: "
                + koalasFound + "/" + TOTAL_KOALAS
                + ". Find the koala, then bring it to C.");

        carryingKoala = false;
    }

    private void redrawGrid() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {

                Label tile = tiles[r][c];
                tile.setText("");
                tile.setGraphic(null);

                char cell = map[r][c];
                ImageView graphic = null;

                if (r == playerRow && c == playerCol) {
                    if (justHitFire && rescuerFireImage != null) {
                        graphic = new ImageView(rescuerFireImage);
                    } else if (carryingKoala && rescuerKoalaImage != null) {
                        graphic = new ImageView(rescuerKoalaImage);
                    } else if (rescuerImage != null) {
                        graphic = new ImageView(rescuerImage);
                    }
                } else {
                    switch (cell) {
                        case 'T' -> {
                            if (treeImage != null) {
                                graphic = new ImageView(treeImage);
                            }
                        }
                        case 'F' -> {
                            if (fireImage != null) {
                                graphic = new ImageView(fireImage);
                            }
                        }
                        case 'K' -> {
                            if (fireImage != null) {
                                graphic = new ImageView(fireImage);
                            }
                        }
                        case 'C' -> {
                            if (rescueCenterImage != null) {
                                graphic = new ImageView(rescueCenterImage);
                            }
                        }
                        default -> {

                        }
                    }
                }

                if (graphic != null) {
                    graphic.setFitWidth(30);
                    graphic.setFitHeight(30);
                    graphic.setPreserveRatio(true);
                    tile.setGraphic(graphic);
                }
            }
        }
    }

    private void handleKey(KeyCode code) {
        if (gameOver) return;

        justHitFire = false;

        int newRow = playerRow;
        int newCol = playerCol;

        switch (code) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
            default -> {
                return;
            }
        }

        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS) {
            return;
        }

        char target = map[newRow][newCol];

        if (target == 'T') {
            statusLabel.setText("The way is blocked by trees. Find another path.");
            return;
        }

        if (target == 'F') {
            playerRow = newRow;
            playerCol = newCol;

            justHitFire = true;

            health--;
            updateHealthLabel();

            if (health <= 0) {
                redrawGrid();
                showGameOver();
            } else {
                statusLabel.setText("The flames burn you! You lost 1 health.");
                redrawGrid();
            }
            return;
        }

        if (target == 'K') {
            playerRow = newRow;
            playerCol = newCol;
            carryingKoala = true;
            map[newRow][newCol] = '.';
            statusLabel.setText("You found a koala! Bring it to the rescue center (C).");
            redrawGrid();
            return;
        }

        if (target == 'C') {
            playerRow = newRow;
            playerCol = newCol;

            if (carryingKoala) {
                koalasFound++;
                carryingKoala = false;

                SceneController.addToScore(1);

                if (koalasFound < TOTAL_KOALAS) {
                    currentLevel++;
                    statusLabel.setText("You delivered a koala to safety! ("
                            + koalasFound + "/" + TOTAL_KOALAS + ") Moving to the next area...");
                    buildLevel();
                    redrawGrid();
                } else {
                    redrawGrid();
                    showWinMessage();
                }
            } else {
                statusLabel.setText("This is the rescue center. Find a koala and bring it here.");
                redrawGrid();
            }
            return;
        }
        if (target == '.') {
            playerRow = newRow;
            playerCol = newCol;
            redrawGrid();
        }
    }

    private void showGameOver() {
        gameOver = true;

        titleLabel.setText("The koalas couldn’t all be saved…");
        infoLabel.setText("Wildfires destroy koalas’ homes and families.");
        infoLabel.setStyle("-fx-font-size: 10px;");
        statusLabel.setText("Koalas are close to extinction. Protect forests. Prevent fires. Speak up.");
        statusLabel.setStyle("-fx-font-size: 10px;");

        gameGrid.getChildren().clear();
        gameGrid.setAlignment(Pos.CENTER);

        if (deadKoalaImage != null) {
            ImageView iv = new ImageView(deadKoalaImage);
            iv.setPreserveRatio(true);
            iv.setFitWidth(150);
            gameGrid.add(iv, 0, 0);
        }

        startButton.setVisible(true);
        startButton.setManaged(true);
    }

    private void showWinMessage() {
        gameOver = true;

        titleLabel.setText("You rescued all 4 koalas!");
        infoLabel.setText("You found each koala and brought them to the rescue center.");
        infoLabel.setStyle("-fx-font-size: 10px;");

        statusLabel.setText("A cigarette is temporary. Extinction is forever.");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        gameGrid.getChildren().clear();
        gameGrid.setAlignment(Pos.CENTER);

        if (victoryImage != null) {
            ImageView iv = new ImageView(victoryImage);
            iv.setPreserveRatio(true);
            iv.setFitWidth(180);
            gameGrid.add(iv, 0, 0);
        }

        Button backToMapButton = new Button("Back to Map");
        backToMapButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px;");
        backToMapButton.setOnAction(event -> goBackToMap());

        gameGrid.add(backToMapButton, 0, 1);

        startButton.setVisible(true);
        startButton.setManaged(true);


    }

    @FXML
    private void goBackToMap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("map.fxml"));
            Parent mapRoot = loader.load();

            Stage stage = (Stage) rootBox.getScene().getWindow();
            Scene mapScene = new Scene(mapRoot);

            boolean wasFullScreen = stage.isFullScreen();
            stage.setScene(mapScene);

            if (wasFullScreen) {
                stage.setFullScreen(true);
            }
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
