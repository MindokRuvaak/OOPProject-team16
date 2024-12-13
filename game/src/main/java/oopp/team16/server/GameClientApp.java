package oopp.team16.server;

import java.util.Scanner;
import java.util.logging.Logger;

public class GameClientApp {
    private static final Logger logger = Logger.getLogger(GameClientApp.class.getName());
    private static final String SERVER_ADDRESS_REGEX = "^(localhost|\\d{1,3}(\\.\\d{1,3}){3})$";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String serverAddress = getServerAddress(scanner);
        System.out.println("Enter server port:");
        int port = Integer.parseInt(scanner.nextLine());

        logger.info("Attempting to connect to server at " + serverAddress + ":" + port + "...");
        try {
            GameClient gameClient = new GameClient(serverAddress, port);
            GameClientController controller = new GameClientController(gameClient);
            controller.start();
        } catch (RuntimeException e) {
            logger.severe("Could not connect to server: " + e.getMessage());
        } finally {
            scanner.close();
            logger.info("GameClientApp has stopped.");
        }
    }

    private static String getServerAddress(Scanner scanner) {
        while (true) {
            System.out.println("Enter the server address (e.g., localhost or an IP):");
            String serverAddress = scanner.nextLine().trim();
            if (serverAddress.matches(SERVER_ADDRESS_REGEX)) {
                return serverAddress;
            }
            logger.warning("Invalid server address provided: " + serverAddress);
        }
    }
}
