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

    public void start() {
        Scanner scanner = new Scanner(System.in);
        listenForServerMessages();
        // Loop to send user commands
        while (true) {
            System.out.println("Enter a command:");
            String command = scanner.nextLine();

            // Parse command into a GameMessage
            GameMessage message = parseCommand(command);

            if (message != null) {
                gameClient.sendMessage(message); // Send the structured message
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void listenForServerMessages() {
        try {
            new Thread(() -> {
                while (true) {
                    String jsonMessage = gameClient.receiveMessage(); // Read JSON string from server
                    if (jsonMessage != null) {
                        GameMessage receivedMessage = new Gson().fromJson(jsonMessage, GameMessage.class); // Deserialize JSON to GameMessage object
                        processServerMessage(receivedMessage); // Process the message based on its type
                    }
                }
            }).start();
        }
        catch(Exception e){
                logger.severe("Error while listening to server: " + e.getMessage());
            }
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




