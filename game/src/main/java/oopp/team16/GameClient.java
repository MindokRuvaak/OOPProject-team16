package oopp.team16;

import java.io.*;
import java.net.Socket;

public class GameClient {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public GameClient(String serverAddress, int port) {
        try{
            clientSocket = new Socket(serverAddress, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connected to server at " + serverAddress + ":" + port);
        } catch (IOException e) {
            System.out.println("Error connecting to server");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void listenForUpdates(GameController controller) {
        new Thread(() -> {
            String serverMessage;
            try {
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println("Received update from server: " + serverMessage);
                    //controller.handleServerUpdate(serverMessage);  // Notify the controller or view of the update
                }
            } catch (IOException e) {
                System.out.println("Error receiving updates from server");
                e.printStackTrace();
            }
        }).start();
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing client connection");
            e.printStackTrace();
        }
    }

    public void start() {
        // Example of sending a message to the server
        try {
            out.println("beep boop");

            // Read messages from the server
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Received from server: " + serverMessage);
            }
        } catch (IOException ex) {
            System.out.println("Error in communication with server.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example of connecting to the server at localhost on port 12345
        GameClient client = new GameClient("localhost", 12345);
        client.start();
    }
}
