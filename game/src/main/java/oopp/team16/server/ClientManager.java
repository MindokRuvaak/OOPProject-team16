package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager extends MessageHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());
    private final Socket clientSocket;
    private final GameServer gameServer; // Reference to GameServer for forwarding messages
    private volatile boolean running = true;

    public ClientManager(Socket socket, GameServer gameServer) {
        this.clientSocket = socket;
        this.gameServer = gameServer;
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
            forwardMessageToServer(message);
        }
    }

    private void forwardMessageToServer(GameMessage message) {
        try {
            gameServer.processClientMessage(message, this);
        } catch (Exception e) {
            logger.warning("Error processing client message: " + e.getMessage());
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
