package oopp.team16.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class GameClientApp {
    private static final Logger logger = Logger.getLogger(GameClientApp.class.getName());

    public static void main(String[] args) {
        final String serverAddress = "127.0.0.1"; // Default to localhost
        final int serverPort = 1234; // Default port
        int numberOfClients = 4;

        ExecutorService executor = Executors.newFixedThreadPool(numberOfClients);

        try {
            for (int i = 0; i < numberOfClients; i++) {
                final int clientId = i + 1;
                executor.submit(() -> {
                    GameClient gameClient = new GameClient(serverAddress, serverPort);
                    try {
                        logger.info(String.format("Client %d connected to server.", clientId));
                        Thread.sleep(5000); // Simulate some activity
                    } catch (Exception e) {
                        logger.severe(String.format("Client %d encountered an error: %s", clientId, e.getMessage()));
                    } finally {
                        gameClient.closeClientConnection();
                        logger.info(String.format("Client %d disconnected.", clientId));
                    }
                });
            }
        } finally {
            executor.shutdown();
        }
    }
}
