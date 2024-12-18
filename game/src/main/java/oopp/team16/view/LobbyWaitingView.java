package oopp.team16.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyWaitingView {
    private Stage primaryStage;
    private Scene scene;

    public LobbyWaitingView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeUI();
    }

    private void initializeUI() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LobbyView.fxml"));
            Parent root = loader.load();

            // Set up the scene
            scene = new Scene(root, 600, 400);

            // Configure the stage
            primaryStage.setTitle("UNO Lobby");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load LobbyView.fxml");
        }
    }

    public void show() {
        primaryStage.show();
    }
}

