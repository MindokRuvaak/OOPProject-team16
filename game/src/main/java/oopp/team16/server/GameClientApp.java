package oopp.team16.server;

import java.util.Scanner;

public class GameClientApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverAddress;
        int port;

        while (true) { //kollar så att address är valid, går att göra bättre med regex? det är ju antingen localhost, eller siffror och punkter.
            System.out.println("Enter the server address (e.g., localhost or an IP):");  //allt detta behövs ändras, gameclientapp kan fortfarande existera som gameclient_init(?)
                                                                                         // och ta in samma parametrar från user input. vi slipper då while-loops, men behöver andra checks istället.
            serverAddress = scanner.nextLine();
            if (!serverAddress.isEmpty()) break;
            System.out.println("Server address cannot be empty.");
        }

        while (true) { //kollar så att port är valid, tror den är fine.
            try {
                System.out.println("Enter the server port (e.g., 12345):");
                port = Integer.parseInt(scanner.nextLine());
                if (port > 0 && port <= 65535) break; // valid port range
                System.out.println("Invalid port. Please enter a number between 1 and 65535.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric port.");
            }
        }

        try {
            GameClient gameClient = new GameClient(serverAddress, port);
            gameClient.getController().start();

        } catch (RuntimeException e) {
            System.out.println("Could not connect to server. Please try again later.");
        } finally {
            scanner.close();
        }
    }
}
