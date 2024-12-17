package oopp.team16.server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class GameServerApp {
    private static final Logger logger = Logger.getLogger(GameServerApp.class.getName());

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        logger.info("Enter server port:");
        int port = scanner.nextInt();

        logger.info("Enter max number of players:");
        int maxPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left over

        GameServer gameServer = null;

        try {
            logger.info("Initializing GameServer...");
            gameServer = new GameServer(port, maxPlayers);
            gameServer.startup();

            // Keep the server running until it is explicitly shut down
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutdown signal received. Shutting down the server...");
                gameServer.shutdown();
            }));

            logger.info("GameServer is running. Waiting for players...");

            // Block the main thread to keep the server alive
            while (gameServer.isRunning()) {
                Thread.sleep(1000); // Prevent busy-waiting
            }
        } catch (Exception e) {
            logger.severe("An error occurred while running the GameServer: " + e.getMessage());
        } finally {
            if (gameServer != null) {
                gameServer.shutdown();
            }
            logger.info("GameServerApp has stopped.");
        }
    }
}
