package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class GameClient extends MessageHandler {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private Socket clientSocket;

    public GameClient(String serverAddress, int port) {
        connectToServer(serverAddress, port);
    }

    public void connectToServer(String serverAddress, int port) {
        try {
            clientSocket = new Socket(serverAddress, port);
            initializeStreams(clientSocket.getInputStream(), clientSocket.getOutputStream());
            logger.info("Connected to server: " + serverAddress + ":" + port);
        } catch (IOException e) {
            logger.severe("Error connecting to server: " + e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    public void closeClientConnection() {
        logger.info("Closing client connection...");
        try {
            closeStreams();
            clientSocket.close();
            logger.info("Client socket closed.");
        } catch (IOException e) {
            logger.severe("Error during client connection closure: " + e.getMessage());
        }
    }
}






