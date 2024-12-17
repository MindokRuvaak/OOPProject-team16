package oopp.team16.server;

import com.google.gson.Gson;

import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());
    //private static final String SERVER_ADDRESS_REGEX = "^(localhost|\\d{1,3}(\\.\\d{1,3}){3})$";

    private final GameClient gameClient;
    private final Gson gson = new Gson();
    private volatile boolean running = true;

    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
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
            case "turn_update":
                handleTurnUpdate(message);
                break;

            case "card_played":
                handleCardPlayed(message);
                break;

            case "game_state":
                handleGameState(message);
                break;

            case "game_over":
                handleGameOver(message);
                break;

            case "invalid_action":
                handleInvalidAction(message);
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }

    // Specific server message handlers
    private void handleTurnUpdate(GameMessage message) {
        String currentPlayer = (String) message.getData().get("current_player");
        logger.info("It's " + currentPlayer + "'s turn.");
        // Update the view to highlight the current player's turn
    }

    private void handleCardPlayed(GameMessage message) {
        String player = (String) message.getData().get("player");
        String card = (String) message.getData().get("card");
        logger.info(player + " played " + card);
        // Update the view to reflect the played card
    }

    private void handleGameState(GameMessage message) {
        logger.info("Game state updated: " + message.getData());
        // Update the local game state and refresh the view
    }

    private void handleGameOver(GameMessage message) {
        String winner = (String) message.getData().get("winner");
        logger.info("Game Over! Winner: " + winner);
        // Display a game-over screen in the view
    }

    private void handleInvalidAction(GameMessage message) {
        String reason = (String) message.getData().get("reason");
        logger.warning("Invalid action: " + reason);
        // Show an error message to the user
    }
}
