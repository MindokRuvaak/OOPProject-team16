package oopp.team16.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopp.team16.controller.GameViewController;
import oopp.team16.server.GameClient;

import java.io.IOException;

public class GameView {

    private Stage primaryStage;
    private Scene scene;
    private int playerCount;
    private GameViewController controller;

    public GameView(Stage primaryStage, GameViewController controller) {
        this.primaryStage = primaryStage;
        this.playerCount = controller.numPlayersConnected();
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        try {
            FXMLLoader loader;
            // Load different FXML files depending on the number of players
            switch (playerCount) {
                case 2 -> loader = new FXMLLoader(getClass().getResource("/MainGUI.fxml"));
                case 3 -> loader = new FXMLLoader(getClass().getResource("/MainGUI3.fxml"));
                case 4 -> loader = new FXMLLoader(getClass().getResource("/MainGUI4.fxml"));
                default -> throw new IllegalArgumentException("Invalid player count");
            }
            loader.setController(this.controller);

            Parent root = loader.load();
            scene = new Scene(root, 1080, 720);

            primaryStage.setTitle("UNO Game - " + playerCount + " Players");
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load GameView.fxml for " + playerCount + " players");
        }
    }


    public void show() {
        primaryStage.show();
    }
}
