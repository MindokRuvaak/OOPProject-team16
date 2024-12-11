package oopp.team16.server;

import com.google.gson.Gson;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());
    private final GameClient gameClient;
    private final Gson gson = new Gson();
    private volatile boolean running = true;

    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void start() {
        listenForServerMessages();
        handleUserCommands();
    }

    private void handleUserCommands() {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter commands in JSON format or type 'exit' to quit:");

        while (true) {
            String command = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(command)) {
                logger.info("Calling GameClient.closeClientConnection...");
                running = false;
                gameClient.closeClientConnection();
                logger.info("Finished calling GameClient.closeClientConnection.");
                break;
            }

            if (command.isEmpty()) {
                logger.warning("Empty command received. Please enter a valid JSON command.");
                continue;
            }

            try {
                GameMessage message = gson.fromJson(command, GameMessage.class);
                if (message != null && message.getType() != null) {
                    gameClient.sendMessage(message);
                    logger.info("Sent message to server: " + message);
                } else {
                    logger.warning("Invalid JSON format. Ensure the command contains a 'type' field.");
                }
            } catch (Exception e) {
                logger.severe("Error parsing JSON command: " + e.getMessage());
                logger.warning("Invalid command. Please enter a valid JSON string.");
            }
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
        if (message == null || message.getType() == null) {
            logger.warning("Received null or malformed GameMessage from server.");
            return;
        }

        logger.info("Processing message of type: " + message.getType());

        switch (message.getType()) {
            case "turn_update":
                logger.info("It's " + message.getData().get("current_player") + "'s turn.");
                break;

            case "card_played":
                logger.info(message.getData().get("player") + " played " + message.getData().get("card"));
                break;

            case "game_state":
                logger.info("Game state updated.");
                break;

            case "game_over":
                logger.info("Game Over! Winner: " + message.getData().get("winner"));
                break;

            case "invalid_action":
                logger.warning("Invalid action: " + message.getData().get("reason"));
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }
}
