package oopp.team16.server;

import com.google.gson.Gson;
import oopp.team16.controller.GameViewController;

import java.util.Map;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    private final GameClient gameClient;
    private volatile boolean running = true;
    private final GameViewController viewController; // Reference to the GameViewController

    public GameClientController(GameClient gameClient, GameViewController viewController) {
        this.gameClient = gameClient;
        this.viewController = viewController;
    }

    public void connect(String serverAddress, int port) {
        try {
            gameClient.connectToServer(serverAddress, port);
            logger.info("Successfully connected to server.");
            listenForServerMessages();
        } catch (RuntimeException e) {
            logger.severe("Failed to connect: " + e.getMessage());
            throw e;
        }
    }

    public void disconnect() {
        running = false;
        gameClient.closeClientConnection();
        logger.info("Disconnected from the server.");
    }

    public void sendMessage(GameMessage message) {
        if (gameClient.isConnected()) {
            gameClient.sendMessage(message);
            logger.info("Sent message to server: " + message);
        } else {
            logger.warning("Cannot send message. Not connected to a server.");
        }
    }

    private void listenForServerMessages() {
        new Thread(() -> {
            while (running) {
                GameMessage receivedMessage = gameClient.receiveMessage();
                if (receivedMessage != null) {
                    processServerMessage(receivedMessage);
                }
            }
            logger.info("Stopped listening for server messages.");
        }).start();
    }

    private void processServerMessage(GameMessage message) {
        logger.info("Processing message of type: " + message.getType());

        switch (message.getType()) {
            case "gameState":
                handleGameState(message);
                break;

            case "gameOver":
                handleGameOver(message);
                break;

            case "invalid_action":
                handleInvalidAction(message);
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }

    private void handleGameState(GameMessage message) {
        String topCard = (String) message.getData().get("topCard");
        String currentPlayer = (String) message.getData().get("currentPlayer");
        Map<String, Double> rawHands = (Map<String, Double>) message.getData().get("hands");

        Map<String, Integer> hands = convertHandSizes(rawHands);

        viewController.updateGameState(topCard, currentPlayer, hands);
    }

    private void handleGameOver(GameMessage message) {
        String winner = (String) message.getData().get("winner");
        logger.info("Game Over! Winner: " + winner);

        viewController.showWinner(winner);
    }

    private void handleInvalidAction(GameMessage message) {
        String reason = (String) message.getData().get("reason");
        logger.warning("Invalid action: " + reason);
        //viewController.displayError("Invalid Action: " + reason);
    }

    private Map<String, Integer> convertHandSizes(Map<String, Double> rawHands) {
        Map<String, Integer> hands = new java.util.HashMap<>();
        for (Map.Entry<String, Double> entry : rawHands.entrySet()) {
            hands.put(entry.getKey(), entry.getValue().intValue());
        }
        return hands;
    }
}
