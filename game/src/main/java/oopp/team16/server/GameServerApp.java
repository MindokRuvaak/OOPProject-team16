package oopp.team16.server;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServerApp {
    private static final Logger logger = Logger.getLogger(GameServerApp.class.getName());
    private static final Gson gson = new Gson();

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

            while (true) {
                logger.info("Awaiting command (JSON or 'shutdown'):");
                String command = scanner.nextLine().trim();

                if ("shutdown".equalsIgnoreCase(command)) {
                    break;
                }

                try {
                    GameMessage message = gson.fromJson(command, GameMessage.class);
                    if (message != null && message.getType() != null) {
                        logger.info("Broadcasting message: " + message);
                        gameServer.broadcastMessage(message);
                    } else {
                        logger.warning("Invalid GameMessage structure. Ensure the JSON has a 'type' field.");
                    }
                } catch (Exception e) {
                    logger.warning("Failed to parse command as JSON. Ensure it is a valid GameMessage.");
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while running the server: " + e.getMessage(), e);
        } finally {
            if (gameServer != null) {
                logger.info("Shutting down the GameServer...");
                gameServer.shutdown();
            }
            scanner.close();
            logger.info("GameServerApp has stopped.");
        }
    }
}
