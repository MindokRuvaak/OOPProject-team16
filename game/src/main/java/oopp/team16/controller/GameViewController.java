package main.java.oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;


public class GameViewController {

    @FXML
    private Label labelLogo;

    @FXML
    private ImageView iconDeck;

    @FXML
    private ImageView iconLastCard;

    @FXML
    private Label labelCurrentPlayer;

    @FXML
    private Circle circleWishColor;

    @FXML
    private Label labelWishColor;

    @FXML
    private ImageView imageViewWishColor;

    @FXML
    private HBox hboxInfo;

    @FXML
    private Label labelInfo;

    @FXML
    private Button buttonInfo;

    @FXML
    private Label labelChallengeCounter;

    @FXML
    private Label labelPlayer2Name;

    @FXML
    private Label labelPlayer3Name;

    @FXML
    private Label labelPlayer1Name;

    @FXML
    private Button buttonStart;

    @FXML
    private MenuItem menuItemBack;

    @FXML
    private Button buttonNewGame;

    @FXML
    private Button buttonSettings;

    public void initialize() {
        buttonInfo.setOnAction(event -> {
            hboxInfo.setVisible(false);
        });

        buttonStart.setOnAction(event -> {
            labelLogo.setText("Game Started!");
            buttonStart.setDisable(true);
        });

        menuItemBack.setOnAction(event -> {
            System.out.println("Back to Main Menu");
        });
    }

    // further event handling methods
}
