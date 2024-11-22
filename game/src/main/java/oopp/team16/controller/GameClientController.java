package oopp.team16.controller;

import java.util.Scanner;

public class GameClientController {
    private final GameClient gameClient;

    public GameClientController(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your player name:");
        String playerName = scanner.nextLine();

        gameClient.sendMessage("NAME:" + playerName);

        while (true) {
            System.out.println("Enter a command:");
            String command = scanner.nextLine();

            gameClient.sendMessage(command);
        }
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient("localhost", 12345);
        GameClientController controller = new GameClientController(gameClient);
        controller.start();
    }
}
