package oopp.team16.server;

import java.util.logging.Logger;
import java.util.Scanner;

public class GameServerApp {
    private static final Logger logger = Logger.getLogger(GameServerApp.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter server port:");
        int port = scanner.nextInt();

        System.out.println("Enter max number of players:");
        int maxPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left over

        GameServer gameServer = null;

        try {
            gameServer = new GameServer(port, maxPlayers);
            gameServer.startup();
            System.out.println("Server started successfully.");
            System.out.println("Type 'shutdown' to stop the server, or type other commands to simulate gameplay.");

            while (true) {
                System.out.println("Send a command to all connected clients:");
                String command = scanner.nextLine();
                if ("shutdown".equalsIgnoreCase(command.trim())) {
                    System.out.println("Shutting down the server...");
                    break;
                }
                gameServer.broadcastMessage(command);
            }
        } catch (Exception ex) {
            logger.severe("An error occurred: " + ex.getMessage());
            for (StackTraceElement element : ex.getStackTrace()) {
                logger.severe(element.toString());
            }
        } finally {
            if (gameServer != null) {
                gameServer.shutdown();
            }
            scanner.close();
            System.out.println("Server has stopped.");
        }
    }
}
