package oopp.team16.controller;

import javafx.fxml.FXML;
import oopp.team16.server.GameClient;
import oopp.team16.server.GameClientController;

import java.awt.*;

public class portController {
    @FXML
    private TextField serverAddressField;

    @FXML
    private TextField serverPortField;

    @FXML
    private Button connectButton;

    @FXML
    private Label connectionStatusLabel;

    private GameClientController gameClientController;

    @FXML
    public void initialize() {
    //    gameClientController = new GameClientController(new GameClient());
    }

    @FXML
    public void handleConnectButtonClick() {
        String serverAddress = serverAddressField.getText().trim();
        String portText = serverPortField.getText().trim();

        try {
            int port = Integer.parseInt(portText);
           // gameClientController.connect(serverAddress, port);
            connectionStatusLabel.setText("Connected to " + serverAddress + ":" + port);
        } catch (NumberFormatException e) {
            connectionStatusLabel.setText("Port must be a valid number.");
        } catch (RuntimeException e) {
            connectionStatusLabel.setText("Failed to connect: " + e.getMessage());
        }
    }

}
