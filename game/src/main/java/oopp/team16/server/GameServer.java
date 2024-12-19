package oopp.team16.server;

import oopp.team16.model.Model;
import oopp.team16.model.ModelListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class GameServer implements ModelListener{
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    private final int port;
    private final int maxPlayers;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    //göra server till modellistener?
    private Model model;
    private ServerMessageHandler messageHandler;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        startup();
        logger.info("Server started on port " + port + ". Waiting for players...");
    }

    public void startup() {
        try {

            serverSocket = new ServerSocket(port);
            model = new Model();
            this.model.addListener(this);
            messageHandler = new ServerMessageHandler(this);
            connectionManager = new ConnectionManager(serverSocket, maxPlayers, this);

            new Thread(connectionManager::acceptConnections, "ConnectionManagerThread").start();

        } catch (IOException e) {
            logger.severe("Unexpected error during GameServer startup: " + e.getMessage());
            shutdown();
        }
        running = true;
    }

    public void shutdown() {
        running = false;

        try {
            logger.info("Closing server socket...");
            connectionManager.closeConnections();
            serverSocket.close();

            logger.info("GameServer shutdown completed.");
        } catch (IOException e) {
            logger.warning("Error during shutdown: " + e.getMessage());
        }
    }

    @Override
    public void requestPlayers() {
        // Broadcast a request for players to join
        GameMessage requestPlayersMessage = new GameMessage("requestPlayers");
        broadcastMessage(requestPlayersMessage);
    }

    @Override
    public void takeTurn() {
        // Notify all clients to update their game state
        broadcastGameState();
    }

    @Override
    public void announceBadMove() {
        // Notify the current player of a bad move
        GameMessage badMoveMessage = new GameMessage("badMove");
        broadcastMessage(badMoveMessage);
    }

    @Override
    public void announceWinner(String name) {
        // Notify all clients of the game winner
        GameMessage winnerMessage = new GameMessage("gameOver");
        winnerMessage.addData("winner", name);
        broadcastMessage(winnerMessage);
    }

    @Override
    public void startNextPlayerTurn() {
        // Notify all clients of the next player's turn
        broadcastGameState();
    }

    @Override
    public void announceMustPlayCard() {
        // Notify the current player that they must play a card
        GameMessage mustPlayCardMessage = new GameMessage("mustPlayCard");
        broadcastMessage(mustPlayCardMessage);
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

    //TODO: denna bör göra mer? händer bör uppdateras.
    public void broadcastGameState() {
        GameMessage gameStateMessage = new GameMessage("gameState");
        gameStateMessage.addData("topCard", model.getTopPlayedCard());
        gameStateMessage.addData("currentPlayer", model.getCurrentPlayerID());

        broadcastMessage(gameStateMessage);
    }

    public synchronized void handlePlayerMove(String playerName, int cardNumber) {
        if (!model.getCurrentPlayerID().equals(playerName)) {
            logger.warning("Invalid move attempt by non-current player: " + playerName);
            return;
        }
        model.playCard(cardNumber);
        broadcastGameState();
    }


    public synchronized void handleEndTurn(String sender) {
        model.endTurn();
        broadcastGameState();
    }

    public synchronized void handleDrawCard(String sender) {
        //drawcard måste kolla att rätt sender kan dra kort
        model.drawCard();
        broadcastGameState();
    }

    public boolean isRunning() {
        return running;
    }
}
