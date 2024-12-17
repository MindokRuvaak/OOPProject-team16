package oopp.team16.server;

import oopp.team16.model.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    private final int port;
    private final int maxPlayers;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    private Model model;
    private ServerMessageHandler messageHandler;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    public void startup() throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            model = new Model();
            connectionManager = new ConnectionManager(serverSocket, maxPlayers, this);
            messageHandler = new ServerMessageHandler(this);
            running = true;

            new Thread(connectionManager::acceptConnections, "ConnectionManagerThread").start();
            logger.info("GameServer started on port " + port);
        } catch (IOException e) {
            logger.severe("Failed to start GameServer: " + e.getMessage());
            shutdown();
            throw e;
        }
    }

    public void shutdown() {
        running = false;
        try {
            if (connectionManager != null) {
                connectionManager.closeConnections();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            logger.info("GameServer shutdown completed.");
        } catch (IOException e) {
            logger.warning("Error shutting down GameServer: " + e.getMessage());
        }
    }

    public void startGame() {
        if (model.getPlayers().isEmpty()) {
            logger.severe("Cannot start the game: No players connected.");
            throw new IllegalStateException("No players in the game.");
        }
        model.initGame();
        model.startGame();
        broadcastGameState();
    }

    public void processClientMessage(GameMessage message, ClientManager sender) {
        messageHandler.handleMessage(message, sender);
    }

    public void broadcastGameState() {
        GameMessage gameStateMessage = new GameMessage("gameState");
        gameStateMessage.addData("topCard", model.getTopPlayedCard());
        gameStateMessage.addData("currentPlayer", model.getCurrentPlayerID());

        broadcastMessage(gameStateMessage);
    }

    public void broadcastMessage(GameMessage message) {
        logger.info("Broadcasting message: " + message);
        connectionManager.getClients().forEach(client -> client.sendMessage(message));
    }

    public boolean isRunning() {
        return running;
    }

    public Model getModel() {
        return model;
    }
}
