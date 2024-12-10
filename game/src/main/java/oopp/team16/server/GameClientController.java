package oopp.team16.server;

import com.google.gson.Gson;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());
    private final GameClient gameClient;


    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    /*public void start() {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        listenForServerMessages();

        // Loop to send user commands
        while (true) {
            logger.info("Enter a JSON command:");
            String command = scanner.nextLine();
            try {
                // Deserialize the command into a GameMessage object
                GameMessage message = gson.fromJson(command, GameMessage.class);

                if (message != null && message.getType() != null) {
                    gameClient.sendMessage(message);
                    logger.info("Sent message: " + message);
                } else {
                    logger.warning("Invalid command format. Ensure the JSON is correctly structured.");
                }
            } catch (Exception e) {
                logger.severe("Error parsing JSON command: " + e.getMessage());
                logger.warning("User entered an invalid JSON command. Prompting again.");
            }
        }
    }*/

    public void start() {
        listenForServerMessages();

        // Send a test message to the server
        GameMessage message = new GameMessage("test_message", "Client1");
        message.addData("content", "This is a test message from the client.");
        gameClient.sendMessage(message);
        logger.info("Sent test message to server: " + message);
    }



    private void listenForServerMessages() {
        new Thread(() -> {
            while (true) {
                GameMessage receivedMessage = gameClient.receiveMessage(); // Now returns a GameMessage
                if (receivedMessage != null) {
                    processServerMessage(receivedMessage);
                }
            }
        }).start();
    }

    private void processServerMessage(GameMessage message) {
        switch (message.getType()) {
            case "turn_update":
                logger.info("It's " + message.getData().get("current_player") + "'s turn.");
                break;
            case "card_played":
                logger.info(message.getData().get("player") + " played " + message.getData().get("card"));
                break;
            case "game_state":
                // Update client-side game state (e.g., update UI)
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




