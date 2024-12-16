package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class GameClient extends MessageHandler {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private Socket clientSocket;

    public GameClient(String serverAddress, int serverPort) {
        connectToServer(serverAddress, serverPort);
    }

    public void connectToServer(String serverAddress, int serverPort) {
        if (clientSocket != null && !clientSocket.isClosed()) {
            logger.warning("Already connected to a server.");
            return;
        }
        try {
            clientSocket = new Socket(serverAddress, serverPort);
            initializeStreams(clientSocket.getInputStream(), clientSocket.getOutputStream());
            logger.info("Connected to server: " + serverAddress + ":" + serverPort);
        } catch (IOException e) {
            logger.severe("Error connecting to server: " + e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    public boolean isConnected() {
        return clientSocket != null && !clientSocket.isClosed();
    }

    public void closeClientConnection() {
        logger.info("Closing client connection...");
        try {
            if (clientSocket != null) {
                closeStreams();
                clientSocket.close();
                logger.info("Client socket closed.");
            }
        } catch (IOException e) {
            logger.severe("Error during client connection closure: " + e.getMessage());
        }
    }
}
