package oopp.team16.controller;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    // Constructor with serverAddress and port
    public GameClient(String serverAddress, int port) {
        connectToServer(serverAddress, port);
    }

    // Method to establish a connection to the server
    public void connectToServer(String serverAddress, int port) {
        try {
            clientSocket = new Socket(serverAddress, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            logger.severe("Error connecting to server: " + e.getMessage());
            throw new RuntimeException("Connection failed", e); // Optional
        }
    }

    // Send a message to the server
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        } else {
            logger.warning("Attempted to send a message but the output stream is null.");
        }
    }

    // Listen for updates from the server
    public void listenForUpdates() {
        new Thread(() -> {
            String serverMessage;
            try {
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println("Received update from server: " + serverMessage);
                }
            } catch (IOException e) {
                logger.warning("Error receiving updates from server: " + e.getMessage());
            }
        }).start();
    }

    // Close the connection
    public void closeConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            logger.warning("Error closing client connection: " + e.getMessage());
        }
    }
}
