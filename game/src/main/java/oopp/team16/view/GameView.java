package oopp.team16.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView {

    private Stage primaryStage;
    private Scene scene;

    public GameView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeUI();
    }

    private void initializeUI() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainGUI.fxml"));
            Parent root = loader.load();

            // Set up the scene
            scene = new Scene(root, 800, 650);

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
}
