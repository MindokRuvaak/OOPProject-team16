package oopp.team16.server;

import java.util.Scanner;

public class GameClientApp {
    private static final String SERVER_ADDRESS_REGEX = "^(localhost|\\d{1,3}(\\.\\d{1,3}){3})$";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverAddress = getServerAddress(scanner);
        int port = getServerPort(scanner);

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

    private static String getServerAddress(Scanner scanner) {
        while (true) {
            System.out.println("Enter the server address (e.g., localhost or an IP):");
            String serverAddress = scanner.nextLine().trim();
            if (serverAddress.matches(SERVER_ADDRESS_REGEX)) {
                return serverAddress;
            }
            System.out.println("Invalid server address. Please enter 'localhost' or a valid IPv4 address.");
        }
    }

    private static int getServerPort(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter the server port (e.g., 12345):");
                int port = Integer.parseInt(scanner.nextLine().trim());
                if (port > 0 && port <= 65535) {
                    return port;
                }
                System.out.println("Invalid port. Please enter a number between 1 and 65535.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric port.");
            }
        }
    }
}
