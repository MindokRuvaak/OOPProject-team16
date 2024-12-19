package oopp.team16.server;

import oopp.team16.model.Model;
import oopp.team16.model.ModelListener;
import oopp.team16.model.gameLogic.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GameServer implements ModelListener{
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    private final int port;
    private final int maxPlayers;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    private Model model;
    private ServerMessageHandler messageHandler;
    private final Map<GameClient, Player> clientToPlayerMap = new HashMap<>();


    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    public synchronized void startup() {
        try {

            model = new Model();
            this.model.addListener(this);

            serverSocket = new ServerSocket(port);
            messageHandler = new ServerMessageHandler(this);

            connectionManager = new ConnectionManager(serverSocket, maxPlayers, this);
            new Thread(connectionManager::acceptConnections, "ConnectionManagerThread").start();

            running = true;
            logger.info("Server started on port " + port + ". Waiting for players...");

        } catch (IOException e) {
            logger.severe("Unexpected error during GameServer startup: " + e.getMessage());
            shutdown();
        }
    }

    public synchronized void shutdown() {
        if (!running) {
            logger.info("GameServer is already shut down.");
            return;
        }

        running = false;
        try {
            logger.info("Shutting down GameServer...");
            connectionManager.closeConnections();
            serverSocket.close();
            logger.info("GameServer shutdown completed.");
        } catch (IOException e) {
            logger.warning("Error during shutdown: " + e.getMessage());
        }
    }

    public synchronized void startGame() {
        logger.info("Starting the game...");
        model.initGame();
        model.startGame();
        broadcastGameState();
        logger.info("Game has started successfully.");
    }

    public void processClientMessage(GameMessage message) {
        messageHandler.handleMessage(message);
    }

    public void broadcastMessage(GameMessage message) {
        logger.info("Broadcasting message: " + message);
        connectionManager.getClients().forEach(client -> client.sendMessage(message));
    }

    //TODO: denna bör göra mer? händer bör uppdateras. //Jag tror den här är fine nu? det här ska bara uppdatera view
    public void broadcastGameState() {
        GameMessage gameStateMessage = new GameMessage("gameState");
        broadcastMessage(gameStateMessage);
    }

    public synchronized void handlePlayCard(int sender, int cardNumber) {
        if (validSender(sender)) {
            model.playCard(cardNumber);
            broadcastGameState();
        }
    }

    public synchronized void handleEndTurn(int sender) {
        if (validSender(sender)) {
            model.endTurn();
            model.nextPlayerTurn();
            broadcastGameState();
        }
    }

    public synchronized void handleDrawCard(int sender) {
        if (validSender(sender)) {
            model.drawCard();
            broadcastGameState();
        }
    }

    public boolean validSender(int sender) {
        return model.getCurrentPlayerID() == sender;
    }

    public boolean isRunning() {
        return running;
    }

    //TODO: DESSA?
    @Override
    public void requestPlayers(int lower, int upper) {
    }

    @Override
    public void announceWinner(int id, int score) {
    }

    @Override
    public void requestWildColor() {
    }
}
