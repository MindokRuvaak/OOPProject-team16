package oopp.team16.server;

import java.util.logging.Logger;
import java.util.Scanner;

public class GameServerApp {
    private static final Logger logger = Logger.getLogger(GameServerApp.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter server port:");
        int port = scanner.nextInt();               // detta får ju ändras på samma sätt som gameclientapp ändras.
                                                    // lättast att kanske ha en start server knapp i settings? idk vad som är bäst

        System.out.println("Enter max number of players:");
        int maxPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        GameServer gameServer = null;

        try {
            //Model model = new Model();   // behövs inte just nu då multiplayer inte har gameplay än
            //JSON för att skicka uppdateringar mellan model och clients med server som intermediary
            gameServer = new GameServer(port, maxPlayers);
            gameServer.startup();
            logger.info("Press Enter to stop the server...");
            scanner.nextLine();

        } catch (Exception ex) {
            logger.severe("An error occurred: " + ex.getMessage());
            for (StackTraceElement element : ex.getStackTrace()) {
                logger.severe(element.toString());
            }
        } finally {
            if (gameServer != null) {
                gameServer.shutdown();            }
            scanner.close();
        }
    }
}
