package oopp.team16.controller;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private final String serverAddress;
    private final int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private volatile boolean running = true;

    public GameClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;

        try {
            // Establish a connection with the server
            clientSocket = new Socket(serverAddress, port);
            logger.info("Connected to server at " + serverAddress + ":" + port);

            // Initialize the output and input streams after the socket is connected
            out = new PrintWriter(clientSocket.getOutputStream(), true); // true enables auto-flush
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Read the server's confirmation message
            String serverMessage = in.readLine(); // This reads the first message from the server
            if (serverMessage != null && serverMessage.equals("Connection Successful!")) {
                logger.info("Successfully connected to the server!");
                // Optional: show a user-friendly confirmation (Console or GUI)
                System.out.println("You have successfully connected to the server!");
            } else {
                logger.warning("Failed to receive confirmation from the server.");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to server", e);
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);  // Send the message
        } else {
            logger.warning("Attempted to send a message but the output stream is null. Ensure the client is connected.");
        }
    }

    public void closeConnection() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing client connection", e);
        }
    }

    public boolean isConnected() {
        return clientSocket != null && clientSocket.isConnected();
    }
}
