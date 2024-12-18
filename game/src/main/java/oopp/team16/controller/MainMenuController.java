package oopp.team16.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import oopp.team16.view.GameView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import oopp.team16.view.LobbyView;
import oopp.team16.view.LobbyWaitingView;
import oopp.team16.view.StartView;


import java.awt.*;
import java.io.IOException;

public class MainMenuController {

    @FXML
    private Label errorS;
    @FXML
    private Label errorS1;
    @FXML
    private TextField port;
    @FXML
    private TextField IP;
    @FXML
    private Button connect;
    @FXML
    private Button uno;
    @FXML
    private Button mainMenuPlayButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button leaveLobbyButton;
    @FXML
    private Button start;
    @FXML
    private Button lobbyPlayButton;

    private int playerCount;

    // Handle the "Play" button click
    @FXML
    public void joinLobby(ActionEvent event) {
        playerCount++;
    }

    @FXML
    public void start(ActionEvent event) throws IOException {
        playerCount = 2;
        if(2 <= playerCount && playerCount <=4) {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            GameView gameView = new GameView(primaryStage, playerCount);
            gameView.show();
        }
      //  else
         //   errorS.setText("cannot start game");
        //    errorS1.setText("must be atleast 2 or max 4 players");
    }

    @FXML
    private void uno(ActionEvent event) throws IOException{
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LobbyView lobbyView = new LobbyView(primaryStage);
        lobbyView.show();
    }
    @FXML
    private void goToLobby(ActionEvent event) throws IOException{
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LobbyWaitingView lobbyWaitingView = new LobbyWaitingView(primaryStage);
        lobbyWaitingView.show();
    }
    @FXML
    private void handleExitButtonClick(){}
}

