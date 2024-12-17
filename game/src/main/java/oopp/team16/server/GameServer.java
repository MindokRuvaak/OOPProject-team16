package oopp.team16.server;

import oopp.team16.model.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    private final int port;
    private final int maxPlayers;
    private volatile boolean running = true;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    private Model model;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    public void startup() throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.model = new Model();
        this.connectionManager = new ConnectionManager(serverSocket, maxPlayers, this);
        this.running = true;

        new Thread(connectionManager::acceptConnections, "ConnectionManagerThread").start();
        logger.info("GameServer started successfully on port " + port);
    }

    public void shutdown() {
        running = false; // Signal that the server is stopping
        try {
            connectionManager.closeConnections();
            serverSocket.close();
            logger.info("GameServer shutdown completed.");
        } catch (IOException e) {
            logger.warning("Error shutting down the server: " + e.getMessage());
        }
    }

    public void processClientMessage(GameMessage message, ClientManager sender) {
        logger.info("Processing message of type: " + message.getType());

        switch (message.getType()) {
            case "playerMove":
                handlePlayerMove(message, sender);
                break;

            case "chatMessage":
                handleChatMessage(message);
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }

    private void handlePlayerMove(GameMessage message, ClientManager sender) {
        String player = message.getSender();
        Object cardPlayed = message.getData().get("cardPlayed");
        //boolean success = model.playCard();
        boolean success = true;

        if (success) {
            broadcastGameState();
        } else {
            sendErrorToClient(sender, "Invalid move!");
        }
    }

    private void handleChatMessage(GameMessage message) {
        connectionManager.getClients().forEach(client -> client.sendMessage(message));
    }

    private void broadcastGameState() {
        GameMessage gameStateMessage = new GameMessage("gameState");
        //gameStateMessage.addData("state", gameModel.getGameState());
        connectionManager.getClients().forEach(client -> client.sendMessage(gameStateMessage));
    }

    private void sendErrorToClient(ClientManager client, String errorMessage) {
        GameMessage errorMessageObject = new GameMessage("error");
        errorMessageObject.addData("message", errorMessage);
        client.sendMessage(errorMessageObject);
    }

    public boolean isRunning() {
        return running;
    }
}
