package oopp.team16.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyView {
    private Stage primaryStage;
    private Scene scene;
    private FXMLLoader loader;

    public LobbyView(Stage primaryStage, Object controller) {
        this.primaryStage = primaryStage;
        // Load the FXML file
        this.loader = new FXMLLoader(getClass().getResource("/PortLobbyView.fxml"));
        loader.setController(controller);
        initializeUI();
    }

    private void initializeUI() {
        try {
            Parent root = loader.load();

            // Set up the scene
            scene = new Scene(root, 250, 400);

            // Configure the stage
            primaryStage.setTitle("UNO Lobby");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load LobbyViw.fxml");
        }
    }

    public void show() {
        primaryStage.show();
    }
}
