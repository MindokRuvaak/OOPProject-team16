package oopp.team16.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import oopp.team16.view.GameView;

import java.io.IOException;

public class LobbyViewController {

    @FXML
    private Button lobbyPlayButton;

    @FXML
    private Button leaveLobbyButton;
    private int playerCount;

    // Handle the "Play" button click
    @FXML
    public void joinLobby(ActionEvent event) {
        playerCount++;
    }

    @FXML
    public void start(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        GameView gameView = new GameView(primaryStage, 2);
        gameView.show();
    }

}

