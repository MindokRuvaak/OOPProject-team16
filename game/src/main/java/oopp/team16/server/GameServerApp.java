package oopp.team16.server;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameServerApp {
    private static final Logger logger = Logger.getLogger(GameServerApp.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int port = promptForInt(scanner, "Enter server port (default: 8080):", 8080);
        int maxPlayers = promptForInt(scanner, "Enter max number of players (default: 4):", 4);

        GameServer gameServer = new GameServer(port, maxPlayers);
        addShutdownHook(gameServer);

        try {
            logger.info("Starting GameServer...");
            gameServer.startup();
            logger.info("GameServer running on port " + port + ", waiting for players...");

            synchronized (GameServerApp.class) {
                GameServerApp.class.wait();
            }
        } catch (InterruptedException e) {
            logger.severe("Main thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.severe("Error running GameServer: " + e.getMessage());
        } finally {
            gameServer.shutdown();
            logger.info("GameServerApp has stopped.");
            scanner.close();
        }
    }

    private static int promptForInt(Scanner scanner, String message, int defaultValue) {
        logger.info(message);
        String input = scanner.nextLine();
        try {
            return input.isEmpty() ? defaultValue : Integer.parseInt(input);
        } catch (NumberFormatException e) {
            logger.warning("Invalid input. Using default: " + defaultValue);
            return defaultValue;
        }
    }

    private static void addShutdownHook(GameServer gameServer) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutdown signal received. Shutting down the server...");
            gameServer.shutdown();
        }));
    }
}
