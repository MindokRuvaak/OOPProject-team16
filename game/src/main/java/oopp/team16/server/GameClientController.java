package oopp.team16.server;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());
    private final GameClient gameClient;

    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);


        // Continue with gameplay interaction
        System.out.println("Enter your player name:");
        String playerName = scanner.nextLine();
        gameClient.sendMessage("NAME:" + playerName);

        // Start sending commands
        while (true) {
            System.out.println("Enter a command:");
            String command = scanner.nextLine();
            gameClient.sendMessage(command);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for the server address and port
        System.out.println("Enter the server address (e.g., localhost or an IP):");
        String serverAddress = scanner.nextLine();
        System.out.println("Enter the server port:");
        int port = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        // Initialize GameClient with the user-provided server address and port
        GameClient gameClient = new GameClient(serverAddress, port);
        GameClientController controller = new GameClientController(gameClient);
        controller.start();  // Start the game client
    }
}