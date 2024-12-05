package oopp.team16.server;

import java.util.Scanner;

public class GameClientApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String serverAddress;
        int port;

        while (true) {
            System.out.println("Enter the server address (e.g., localhost or an IP):");
            serverAddress = scanner.nextLine();
            if (!serverAddress.isEmpty()) break;
            System.out.println("Server address cannot be empty.");
        }

        while (true) {
            try {
                System.out.println("Enter the server port (e.g., 12345):");
                port = Integer.parseInt(scanner.nextLine());
                if (port > 0 && port <= 65535) break; // Valid port range
                System.out.println("Invalid port. Please enter a number between 1 and 65535.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric port.");
            }
        }

        try {
            GameClient gameClient = new GameClient(serverAddress, port);
            GameClientController controller = new GameClientController(gameClient);
            controller.start();
        } catch (RuntimeException e) {
            System.out.println("Could not connect to server. Please try again later.");
        } finally {
            scanner.close();
        }
    }
}
