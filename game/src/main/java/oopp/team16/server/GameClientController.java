package oopp.team16.server;

import java.util.Scanner;
import java.util.logging.Logger;


public class GameClientController {
    private final GameClient gameClient;
    //private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void start() {

        // ny tråd som kan lyssna på servermeddelanden. detta behövs nog men på ett annat sätt när view inte är terminalbaserad
        //new Thread(this::listenForServerMessages).start();
        Scanner scanner = new Scanner(System.in);

        // Main game loop for sending commands // detta bör då också ändras eftersom detta är för en terminalbaserad implementation
        while (true) {
            System.out.println("Enter a command:");
            String command = scanner.nextLine();
            gameClient.sendMessage(command);
        }
    }

    /*private void listenForServerMessages() {
        try {
            // Continuously listen for incoming messages from the server
            while (true) {
                String serverMessage = gameClient.receiveMessage();
                if (serverMessage != null) {
                    logger.info("Server says: " + serverMessage);
                    // You can process the server message here (e.g., update game state)
                }
            }
        } catch (Exception e) {
            logger.severe("Error while listening to server: " + e.getMessage());
        }
    }*/
}
