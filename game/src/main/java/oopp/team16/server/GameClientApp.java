package oopp.team16.server;

import java.util.Scanner;

public class GameClientApp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the server address (e.g., localhost or an IP):");
        String serverAddress = scanner.nextLine();

        System.out.println("Enter the server port:");
        int port = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        GameClient gameClient = new GameClient(serverAddress, port);
        GameClientController controller = new GameClientController(gameClient);

        controller.start();
    }
}
