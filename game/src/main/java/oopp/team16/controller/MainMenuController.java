package oopp.team16.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import oopp.team16.GameStartListener;
import oopp.team16.server.GameClient;
import oopp.team16.server.GameClientController;
import oopp.team16.view.GameView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import oopp.team16.view.LobbyView;
import oopp.team16.view.LobbyWaitingView;
import oopp.team16.view.StartView;

import java.awt.*;
import java.io.IOException;

public class MainMenuController implements GameStartListener{

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
    @FXML
    private TextField serverAddressField;

    @FXML
    private TextField serverPortField;

    @FXML
    private Button connectButton;

    @FXML
    private Label connectionStatusLabel;

    private GameClientController gameClientController;

    private int playerCount;
    GameViewController gameViewController;
    GameView gameView;

    public MainMenuController() {
        this.playerCount = 0;
        this.gameViewController = new GameViewController();
        this.gameClientController = new GameClientController(this.gameViewController);
    }
    // Going to GameView
    @FXML
    public void start(ActionEvent event) throws IOException {
        playerCount = this.gameViewController.numPlayersConnected();
        
        if (2 <= playerCount && playerCount <= 4) {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            this.gameView = new GameView(primaryStage, gameViewController);

            gameClientController.pressedStart();
        }
        // else
        // errorS.setText("cannot start game");
        // errorS1.setText("must be atleast 2 or max 4 players");
    }

    // Going to lobby port
    @FXML
    private void uno(ActionEvent event) throws IOException {
        // going to lobby port
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LobbyView lobbyView = new LobbyView(primaryStage, this);
        lobbyView.show();
    }

    // Going to lobby queue
    @FXML
    private void goToLobby(ActionEvent event) throws IOException {
        boolean connected = false;
        String serverAddress = serverAddressField.getText().trim();
        System.out.println(serverAddress);
        String portText = serverPortField.getText().trim();
        try {
            int port = Integer.parseInt(portText);
            System.out.println(serverPortField);
            this.gameClientController.connect(serverAddress, port);
            System.out.println("Connected to " + serverAddress + ":" + port);
            connected = true;
            // gameViewController.ping();
            connectionStatusLabel.setText("Connected to " + serverAddress + ":" + port);
        } catch (NumberFormatException e) {
            connectionStatusLabel.setText("Port must be a valid number.");
        } catch (RuntimeException e) {
            connectionStatusLabel.setText("Failed to connect: " + e.getMessage());
        }

        if (connected) {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(actionEvent -> {
                // Transition to the lobby view after the delay
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                LobbyWaitingView lobbyWaitingView = new LobbyWaitingView(primaryStage, this);
                lobbyWaitingView.show();
            });

            // Start the pause
            pause.play();
        }
    }

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleExitButtonClick() {
    }
    
    @Override
    public void startGame() {
        this.gameView.show();
    }

}
