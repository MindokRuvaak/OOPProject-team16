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

    public void startup() {
        try {

            serverSocket = new ServerSocket(port);
            model = new Model();
            messageHandler = new ServerMessageHandler(this);
            connectionManager = new ConnectionManager(serverSocket, maxPlayers, this);

            new Thread(connectionManager::acceptConnections, "ConnectionManagerThread").start();

            running = true;

        } catch (IOException e) {
            logger.severe("Failed to start GameServer: " + e.getMessage());
            running = false;
            shutdown();
        } catch (Exception e) {
            logger.severe("Unexpected error during GameServer startup: " + e.getMessage());
            running = false;
            shutdown();
        }
    }

    public void shutdown() {
        if (!running) {
            logger.info("GameServer is already stopped.");
            return;
        }

        running = false;
        logger.info("GameServer running state set to false.");

        try {
            logger.info("Closing server socket...");
            if (connectionManager != null) {
                connectionManager.closeConnections();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            logger.info("GameServer shutdown completed.");
        } catch (IOException e) {
            logger.warning("Error during shutdown: " + e.getMessage());
        }
    }

    public void processClientMessage(GameMessage message, ClientManager sender) {
        messageHandler.handleMessage(message, sender);
    }

    public void broadcastMessage(GameMessage message) {
        logger.info("Broadcasting message: " + message);
        connectionManager.getClients().forEach(client -> client.sendMessage(message));
    }

    public void broadcastGameState() {
        GameMessage gameStateMessage = new GameMessage("gameState");
        gameStateMessage.addData("topCard", model.getTopPlayedCard());
        gameStateMessage.addData("currentPlayer", model.getCurrentPlayerID());

        broadcastMessage(gameStateMessage);
    }

    public synchronized void startGame() {
        logger.info("Starting the game...");
        if (model.getPlayers().isEmpty()) {
            logger.warning("Cannot start the game: No players connected.");
            return;
        }
        model.initGame();
        model.startGame();
        broadcastGameState();
        logger.info("Game has started successfully.");
    }

    public synchronized void handlePlayerMove(ClientManager sender, int cardPlayed) {
        logger.info("Player " + sender.getClientId() + " played card " + cardPlayed);
        model.playCard(cardPlayed);
        broadcastGameState();
    }

    public synchronized void handleEndTurn(ClientManager sender) {
        logger.info("Ending turn for player: " + sender.getClientId());
        model.endTurn();
        broadcastGameState();
    }

    public boolean isRunning() {
        return running;
    }
}
