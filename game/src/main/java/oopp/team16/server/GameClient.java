package oopp.team16.server;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private static final Gson gson = new Gson();
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public GameClient(String serverAddress, int port) {
        connectToServer(serverAddress, port);
    }

    private void connectToServer(String serverAddress, int port) {
        try {
            clientSocket = new Socket(serverAddress, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            logger.info("Connected to " + serverAddress + ":" + port);
        } catch (IOException e) {
            logger.severe("Error connecting to server: " + e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    public void closeConnection() { // detta är för om klienten vill closea connection. ska kallas på av en controller
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            logger.info("Connection to server closed successfully.");
        } catch (IOException e) {
            logger.severe("Error closing connection: " + e.getMessage());
        }
    }

    public void sendMessage(GameMessage message) {
        if (out == null) {
            logger.warning("Cannot send message: Output stream is not initialized.");
            return;
        }
        try {
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);
            out.flush();
            logger.info("Sent message to server: " + jsonMessage);
        } catch (Exception e) {
            logger.severe("Failed to send message: " + e.getMessage());
        }
    }

    public GameMessage receiveMessage() {
        if (in == null) {
            logger.warning("Cannot receive message: Input stream is not initialized.");
            return null;
        }
        try {
            String jsonMessage = in.readLine();
            if (jsonMessage != null) {
                logger.info("Received raw message from server: " + jsonMessage);
                // Deserialize the JSON string into a GameMessage object
                return gson.fromJson(jsonMessage, GameMessage.class);
            } else {
                logger.warning("Received null or end-of-stream from server.");
            }
        } catch (IOException e) {
            logger.severe("Error reading message from server: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Error deserializing message: " + e.getMessage());
        }
        return null;
    }

}
