package oopp.team16.server;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());
    private static final Gson gson = new Gson(); // Shared Gson instance
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private volatile boolean running = true; // For thread management

    public ClientManager(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            initializeStreams();
            listenForMessages();
        } catch (IOException e) {
            logger.warning("Error initializing client connection: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void initializeStreams() throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        logger.info("Streams initialized for client: " + clientSocket.getInetAddress().getHostAddress());
    }

    private void listenForMessages() {
        try {
            while (running) {
                String jsonMessage = in.readLine();
                if (jsonMessage != null) {
                    logger.info("Received raw message from client: " + jsonMessage);
                    try {
                        GameMessage message = gson.fromJson(jsonMessage, GameMessage.class);
                        processMessage(message);
                    } catch (Exception e) {
                        logger.warning("Failed to deserialize message: " + e.getMessage());
                    }
                } else {
                    logger.warning("Received null or end-of-stream from client.");
                    break;
                }
            }
        } catch (IOException e) {
            if (!"Socket closed".equals(e.getMessage())) {
                logger.warning("Error reading from client: " + e.getMessage());
            }
        }
    }

    private void processMessage(GameMessage message) {
        if (message == null || message.getType() == null) {
            logger.warning("Received null or malformed GameMessage from client.");
            return;
        }

        logger.info("Processing message of type: " + message.getType());
        // Implement specific logic for handling different message types
        // Example:
        switch (message.getType()) {
            case "card_played":
                logger.info("Player " + message.getData().get("player") + " played card: " + message.getData().get("card"));
                // Pass message to GameServer or ConnectionManager for processing
                break;
            case "chat_message":
                logger.info("Chat message received: " + message.getData().get("content"));
                break;
            default:
                logger.warning("Unknown message type: " + message.getType());
        }
    }

    public void sendMessageToClient(GameMessage message) {
        if (out == null) {
            logger.warning("Cannot send message: Output stream is not initialized.");
            return;
        }
        if (message == null) {
            logger.warning("Cannot send message: Provided GameMessage is null.");
            return;
        }
        try {
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);
            out.flush();
            logger.info("Sent message to client: " + jsonMessage);
        } catch (Exception e) {
            logger.severe("Failed to send message to client: " + e.getMessage());
        }
    }

    public void closeConnection() {
        running = false; // Stop the message listener
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                logger.info("Connection closed for client: " + clientSocket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            logger.warning("Error closing client connection: " + e.getMessage());
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
