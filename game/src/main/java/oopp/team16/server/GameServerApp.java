package oopp.team16.server;

import com.google.gson.Gson;
import java.util.Scanner;

public class GameServerApp {
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int port = scanner.nextInt();

        int maxPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left over

        GameServer gameServer = null;

        try {
            gameServer = new GameServer(port, maxPlayers);
            gameServer.startup();

            while (true) {
                String command = scanner.nextLine().trim();

                if ("shutdown".equalsIgnoreCase(command)) {
                    break;
                }

                try {
                    GameMessage message = gson.fromJson(command, GameMessage.class);
                    if (message != null && message.getType() != null) {
                        gameServer.broadcastMessage(message);
                    } else {
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception ex) {
            for (StackTraceElement element : ex.getStackTrace()) {
            }
        } finally {
            if (gameServer != null) {
                gameServer.shutdown();
            }
            scanner.close();
        }
    }
}
