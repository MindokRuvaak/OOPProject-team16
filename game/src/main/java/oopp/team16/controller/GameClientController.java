package oopp.team16.controller;

import java.util.Scanner;

public class GameClientController {
    private final GameClient gameClient;

    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Prompt for server address (IP) and player name
        System.out.print("Enter server IP address: ");
        String serverAddress = scanner.nextLine();

        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();

        // Establish the connection with the provided server address and port
        gameClient.connectToServer(serverAddress, 12345);  // Add the port here, too!

        // Send player name after connection is established
        gameClient.sendMessage("NAME:" + playerName);

        // Start interacting with the game
        while (true) {
            System.out.println("Enter a command:");
            String command = scanner.nextLine();
            gameClient.sendMessage(command);
        }
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient("localhost", 12345);  // default values
        GameClientController controller = new GameClientController(gameClient);
        controller.start();
    }
}
