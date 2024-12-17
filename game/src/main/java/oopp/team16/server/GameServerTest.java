package oopp.team16.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class GameServerTest {
    private static final Logger logger = Logger.getLogger(GameServerTest.class.getName());

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 1; // Use the same port as in GameServerApp
        int maxPlayers = 4;

        // Simulate 4 clients connecting to the server
        ExecutorService clientPool = Executors.newFixedThreadPool(maxPlayers);
        for (int i = 1; i <= maxPlayers; i++) {
            final int clientId = i;
            clientPool.submit(() -> {
                try {
                    logger.info("Starting Test Client " + clientId);
                    GameClient client = new GameClient(serverAddress, serverPort);
                    GameClientController controller = new GameClientController(client);

                    // Send a dummy message to the server
                    GameMessage message = new GameMessage("playerMove", "Client" + clientId);
                    message.addData("cardPlayed", "DummyCard-" + clientId);

                    controller.sendMessage(message);
                    logger.info("Client " + clientId + " sent message: " + message);

                    // Wait to simulate client activity
                    Thread.sleep(3000);

                    // Disconnect client
                    controller.disconnect();
                    logger.info("Test Client " + clientId + " disconnected.");
                } catch (Exception e) {
                    logger.severe("Error in Test Client " + clientId + ": " + e.getMessage());
                }
            });
        }

        // Gracefully shut down the client pool
        clientPool.shutdown();
        logger.info("All test clients finished execution.");
    }
}
