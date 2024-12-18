package oopp.team16.server;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameClientApp {
    private static final Logger logger = Logger.getLogger(GameClientApp.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for server address and port
        System.out.print("Enter server address (default: 127.0.0.1): ");
        String serverAddress = scanner.nextLine().trim();
        if (serverAddress.isEmpty()) {
            serverAddress = "127.0.0.1"; // Default to localhost
        }

        System.out.print("Enter server port (default: 1234): ");
        String portInput = scanner.nextLine().trim();
        int serverPort = portInput.isEmpty() ? 1234 : Integer.parseInt(portInput);

        // Create and connect the GameClient
        GameClient gameClient = new GameClient(serverAddress, serverPort);
        try {
            System.out.println("Press Enter to disconnect...");
            scanner.nextLine(); // Wait for user input to disconnect

        } catch (RuntimeException e) {
            logger.severe("Failed to connect to server: " + e.getMessage());
        } finally {
            gameClient.closeClientConnection();
            logger.info("Disconnected from server.");
            scanner.close();
        }
    }
}
