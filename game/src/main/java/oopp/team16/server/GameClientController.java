package oopp.team16.server;

import oopp.team16.controller.GameViewController;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    private final GameClient gameClient;
    private final ClientMessageHandler messageHandler; // Handles incoming messages
    private boolean connected;

    public GameClientController(GameClient gameClient, GameViewController viewController) {
        this.gameClient = gameClient;
        this.messageHandler = new ClientMessageHandler(viewController); // Initialize the handler
        this.connected = false;
    }

    public void connect(String serverAddress, int port) {
        try {
            gameClient.connectToServer(serverAddress, port);
            gameClient.startListening(messageHandler); // Pass the ClientMessageHandler here
            connected = true;
            logger.info("Connected to server and started listening for messages.");
        } catch (RuntimeException e) {
            logger.severe("Failed to connect: " + e.getMessage());
            throw e;
        }
    }

    public void disconnect() {
        if (connected) {
            gameClient.closeClientConnection();
            connected = false;
            logger.info("Disconnected from the server.");
        } else {
            logger.warning("Client is not currently connected.");
        }
    }

    public void sendMessage(GameMessage message) {
        if (connected && gameClient.isConnected()) {
            gameClient.sendMessage(message);
            logger.info("Sent message: " + message);
        } else {
            logger.warning("Cannot send message: Not connected to the server.");
        }
    }

    public void playCard(int cardId) {
        GameMessage message = new GameMessage("playerMove");
        message.setSender("Player"); // Replace with actual player name if available
        message.addData("cardPlayed", cardId);
        sendMessage(message);
    }
}
