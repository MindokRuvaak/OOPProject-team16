package oopp.team16.server;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());
    private final GameClient gameClient;

    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void start() {

        // Start a new thread to listen to server messages
        new Thread(this::listenForServerMessages).start();

        Scanner scanner = new Scanner(System.in);


        // Main game loop for sending commands
        while (true) {
            System.out.println("Enter a command:");
            String command = scanner.nextLine();
            gameClient.sendMessage(command);
        }
    }

    private void listenForServerMessages() {
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
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the server address (e.g., localhost or an IP):");
        String serverAddress = scanner.nextLine();
        System.out.println("Enter the server port:");
        int port = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        GameClient gameClient = new GameClient(serverAddress, port);
        GameClientController controller = new GameClientController(gameClient);
        controller.start();  // Start the game client
    }
}
