package oopp.team16.server;

import oopp.team16.model.Model;

import java.io.IOException;
import java.util.Scanner;

public class GameServerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        // Prompt for the server port
        System.out.println("Enter server port:");
        int port = scanner.nextInt();
        System.out.println("Enter max number of players:");
        int maxPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        try {
            // Initialize the model (business logic)
            Model model = new Model();

            // Create the GameServer instance
            GameServer gameServer = new GameServer(model, maxPlayers, port);

            // Start the server to accept connections
            gameServer.acceptConnections();

            // Inform user that the server is running
            System.out.println("Server is running on port " + port);

            // Keep the server running or handle shutdown later
            System.out.println("Press Enter to stop the server...");
            scanner.nextLine();  // Wait for user input to stop the server

            // Shut down the server when finished
            gameServer.stopServer();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();  // Close the scanner
        }
    }
}
