package oopp.team16.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopp.team16.controller.GameViewController;
import oopp.team16.server.GameClient;
import oopp.team16.server.GameClientController;

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

            // Retrieve the controller
            GameViewController controller = loader.getController();

            // Initialize GameClient and GameClientController
            GameClient client = new GameClient("192.168.50.7", 12345); // Connect to server
            GameClientController clientController = new GameClientController(client);

            // Link the GameClientController to the GameViewController
            controller.setClientController(clientController);

            // Set up the scene
            scene = new Scene(root, 800, 650);

            // Configure the stage
            primaryStage.setTitle("UNO Game");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load MainGUI.fxml");
        }
    }


    public void show() {
        primaryStage.show();
    }
}
