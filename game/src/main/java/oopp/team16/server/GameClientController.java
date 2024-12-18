package oopp.team16.server;

import oopp.team16.controller.GameViewController;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    private GameClient gameClient;
    private final ClientMessageHandler messageHandler;

    public GameClientController(GameViewController viewController) {
        this.messageHandler = new ClientMessageHandler(viewController);
    }

    public void connect(String serverAddress, int port) {
        try {
            gameClient = new GameClient(serverAddress, port);
            gameClient.startListening(messageHandler);
            logger.info("Connected to server and started listening for messages.");
        } catch (RuntimeException e) {
            logger.severe("Failed to connect: " + e.getMessage());
            throw e;
        }
    }

    public void disconnect() {
        if (gameClient != null && gameClient.isConnected()) {
            gameClient.closeClientConnection();
            logger.info("Disconnected from the server.");
        }
    }

    public void playCard(int cardId) {
        GameMessage message = new GameMessage("playerMove");
        message.setSender("Player"); // Replace with actual player name if available
        message.addData("cardPlayed", cardId);
        gameClient.sendMessage(message);
    }

    public void endTurn() {
        GameMessage message = new GameMessage("endTurn");
        message.setSender("Player");
        gameClient.sendMessage(message);
    }
}
