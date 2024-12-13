package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager extends MessageHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());
    private final Socket clientSocket;
    private volatile boolean running = true;

    public ClientManager(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            initializeStreams(clientSocket.getInputStream(), clientSocket.getOutputStream());
            listenForMessages();
        } catch (IOException e) {
            logger.warning("Error initializing client connection: " + e.getMessage());
        } finally {
            closeServerConnection();
        }
    }

    private void listenForMessages() {
        while (running) {
            GameMessage message = receiveMessage();
            if (message == null) {
                logger.info("Client disconnected or invalid message received, stopping listener.");
                break;
            }
            processClientMessage(message);
        }
    }

    private void processClientMessage(GameMessage message) {

        logger.info("Processing message of type: " + message.getType());

        switch (message.getType()) {
            case "playerMove":
                logger.info("Player " + message.getSender() + " played card: " + message.getData().get("cardPlayed"));
                break;

            case "chat_message":
                logger.info("Chat message from " + message.getSender() + ": " + message.getData().get("message"));
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }

    public void closeServerConnection() {
        running = false;
        closeStreams();
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.warning("Error closing client socket: " + e.getMessage());
        }

    }
}
