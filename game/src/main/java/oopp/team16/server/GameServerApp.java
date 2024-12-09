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
            //Model model = new Model();   // behövs inte just nu då multiplayer inte har gameplay än
            //JSON för att skicka uppdateringar mellan model och clients med server som intermediary
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
                // Create a GameMessage from the command
                GameMessage message = parseCommand(command);

                if (message != null) {
                    gameServer.broadcastMessage(message); // Broadcast the GameMessage
                } else {
                    System.out.println("Invalid command. Try again.");
                }
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
