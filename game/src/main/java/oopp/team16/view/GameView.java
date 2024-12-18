package oopp.team16.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView {

    private Stage primaryStage;
    private Scene scene;
    private int playerCount;

    public GameView(Stage primaryStage, int playerCount) {
        this.primaryStage = primaryStage;
        this.playerCount = playerCount;
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
/*
    public GameView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeUI();
    }

    private void initializeUI() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainGUI4.fxml"));
            Parent root = loader.load();

            // Set up the scene
            scene = new Scene(root, 1080, 720);

            // Configure the stage
            primaryStage.setTitle("UNO Game");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load GameView.fxml");
        }
    }

    public void show() {
        primaryStage.show();
    }

 */
}
