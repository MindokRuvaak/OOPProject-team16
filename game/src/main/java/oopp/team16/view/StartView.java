package oopp.team16.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartView {
    private Stage primaryStage;
    private Scene scene;
    private FXMLLoader loader;

    public StartView(Stage primaryStage, Object controller) {
        // Load the FXML file
        this.loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        loader.setController(controller);
        this.primaryStage = primaryStage;
        initializeUI();
    }

    private void initializeUI() {
        try {
            Parent root = loader.load();

            // Set up the scene
            scene = new Scene(root, 600, 400);

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


