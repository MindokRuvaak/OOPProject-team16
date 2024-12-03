package oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.util.Duration;


public class GameViewController {

    @FXML
    private Label labelLogo;

    @FXML
    private Label labelCurrentPlayer;

    @FXML
    private HBox hboxInfo;

    @FXML
    private Label labelInfo;

    @FXML
    private Button buttonInfo;

    @FXML
    private Button buttonPlayDeck;

    @FXML
    private Button buttonLastCard;


    @FXML
    private Label labelPlayer2Name;

    @FXML
    private Label labelPlayer3Name;

    @FXML
    private Label labelPlayer1Name;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonNewGame;

    @FXML private HBox playerHand; // Add this to your FXML file


    private final double CARD_HEIGHT = 90.0;
    private final double CARD_WIDTH = 57.0;

    private final double CARD_SPACING_LARGE = 14.0;
    private final double CARD_SPACING_MEDIUM = 0.0;
    private final double CARD_SPACING_SMALL = - 25.0;
    private final double CARD_SPACING_ULTRA_SMALL = - 35.0;

    private Point2D PLAYER_STARTING_POINT;
    private final Point2D AI_1_STARTING_POINT = new Point2D(100.0, 75.0);
    private Point2D AI_2_STARTING_POINT;
    private Point2D AI_3_STARTING_POINT;


    public void initialize() {
        buttonInfo.setOnAction(event -> {
            hboxInfo.setVisible(false);
        });

        buttonStart.setOnAction(event -> {
            labelLogo.setText("Game Started!");
            buttonStart.setDisable(true);
        });


    }
    // further event handling methods

}
