package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class GameClient extends MessageHandler {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());

    private Socket clientSocket;
    private ClientMessageHandler messageHandler;

    public GameClient(String serverAddress, int serverPort) {
        connectToServer(serverAddress, serverPort);
    }
//TODO: Varför hanterar gameServer kommunikation mellan server och model, men gameclientController hanterar mellan client och view? typ
    public synchronized void connectToServer(String serverAddress, int serverPort) {
        try {
            clientSocket = new Socket(serverAddress, serverPort);
            initializeStreams(clientSocket.getInputStream(), clientSocket.getOutputStream());
            logger.info("Successfully connected to server at: " + serverAddress + ":" + serverPort);
        } catch (IOException e) {
            logger.severe("Failed to connect to server: " + e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    public synchronized boolean isConnected() {
        return clientSocket != null && !clientSocket.isClosed();
    }

    public synchronized void closeClientConnection() {
        if (!isConnected()) {
            logger.info("Client is already disconnected.");
            return;
        }
        try {
            closeStreams();
            clientSocket.close();
            logger.info("Client connection closed successfully.");
        } catch (IOException e) {
            logger.severe("Error while closing client socket: " + e.getMessage());
        }
    }

    public void startListening(ClientMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        new Thread(this::listenForMessages, "ClientListenerThread").start();
        logger.info("Started listening for messages.");
    }

    @Override
    protected void onMessageReceived(GameMessage message) {
        if (messageHandler != null) {
            logger.info("Received message: " + message.getType());
            messageHandler.handleMessage(message);
        } else {
            logger.warning("MessageHandler is not initialized; message ignored.");
        }
    }
}
