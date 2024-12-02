package oopp.team16.server;

import oopp.team16.model.Model;

import java.util.logging.Logger;
import java.util.Scanner;

public class GameServerApp {

    private static final Logger logger = Logger.getLogger(GameServerApp.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for server port and max players
        System.out.println("Enter server port:");
        int port = scanner.nextInt();
        System.out.println("Enter max number of players:");
        int maxPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        GameServer gameServer = null;

        try {
            // Initialize the model (business logic)
            Model model = new Model();

            // Create the GameServer instance with port and maxPlayers
            gameServer = new GameServer(model, port, maxPlayers);

            // Start the server
            gameServer.startup();

            // Inform the user that the server is running
            System.out.println("Server is running on port " + port);
            System.out.println("Press Enter to stop the server...");
            scanner.nextLine(); // Wait for user input to stop the server

        } catch (Exception ex) {
            // Log the exception message and stack trace using the logger
            logger.severe("An error occurred: " + ex.getMessage());
            for (StackTraceElement element : ex.getStackTrace()) {
                logger.severe(element.toString());
            }
        } finally {
            if (gameServer != null) {
                gameServer.getShutdownManager().stopServer(gameServer.getServerSocket());
            }
            scanner.close();
        }
    }
}
