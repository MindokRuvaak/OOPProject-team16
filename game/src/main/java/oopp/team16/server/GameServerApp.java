package oopp.team16.server;

import com.google.gson.Gson;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameServerApp {
    private static final Logger logger = Logger.getLogger(GameServerApp.class.getName());
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        logger.info("Enter server port:");
        int port = scanner.nextInt();

        logger.info("Enter max number of players:");
        int maxPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left over

        GameServer gameServer = null;

        try {
            gameServer = new GameServer(port, maxPlayers);
            gameServer.startup();
            logger.info("Server started successfully.");
            logger.info("Type 'shutdown' to stop the server, or enter a JSON command to broadcast to all clients.");

            while (true) {
                logger.info("Awaiting command (JSON or 'shutdown'):");
                String command = scanner.nextLine().trim();

                if ("shutdown".equalsIgnoreCase(command)) {
                    logger.info("Shutting down the server...");
                    break;
                }

                // Try to deserialize the input into a GameMessage
                try {
                    GameMessage message = gson.fromJson(command, GameMessage.class);
                    if (message != null && message.getType() != null) {
                        gameServer.broadcastMessage(message);
                        logger.info("Broadcasted message: " + message);
                    } else {
                        logger.warning("Invalid GameMessage structure. Ensure the JSON has a 'type' field.");
                    }
                } catch (Exception e) {
                    logger.warning("Failed to parse command as JSON. Ensure it is a valid GameMessage.");
                }
            }
        } catch (Exception ex) {
            logger.severe("An error occurred: " + ex.getMessage());
            for (StackTraceElement element : ex.getStackTrace()) {
                logger.severe(element.toString());
            }
        } finally {
            if (gameServer != null) {
                logger.info("Calling GameServer.shutdown()...");
                gameServer.shutdown();
            }
            scanner.close();
            logger.info("Server has stopped.");
        }
    }
}
