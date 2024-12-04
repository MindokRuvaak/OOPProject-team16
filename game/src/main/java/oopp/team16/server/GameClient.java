package oopp.team16.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public GameClient(String serverAddress, int port) {
        connectToServer(serverAddress, port);
    }

    public void connectToServer(String serverAddress, int port) {
        try {
            clientSocket = new Socket(serverAddress, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            logger.severe("Error connecting to server: " + e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        } else {
            logger.warning("Attempted to send a message but the output stream is null.");
        }
    }

    public String receiveMessage() {
        try {
            if (in != null) {
                return in.readLine(); // Reads a single line from the server
            } else {
                logger.warning("Input stream is null.");
            }
        } catch (IOException e) {
            logger.severe("Error reading from server: " + e.getMessage());
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            logger.severe("Error closing connection: " + e.getMessage());
        }
    }
}