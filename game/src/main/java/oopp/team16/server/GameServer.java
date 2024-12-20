package oopp.team16.server;

import oopp.team16.model.Game;
import oopp.team16.model.Model;
import oopp.team16.model.ModelListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class GameServer implements ModelListener {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    private final int port;
    private final int maxPlayers;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    private Model model;
    private ServerMessageHandler messageHandler;
    private boolean gameStarted;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.gameStarted = false;
    }

    public synchronized void startup() {
        try {
            this.model = new Model();
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
        if (!gameStarted) {
            logger.info("Starting the game...");
            model.initGame();
            model.startGame();
            broadcastGameState();
            logger.info("Game has started successfully.");
            gameStarted = true;
        }
    }

    public void processClientMessage(GameMessage message) {
        messageHandler.handleMessage(message);
    }

    public void broadcastMessage(GameMessage message) {
        logger.info("Broadcasting message: " + message);
        connectionManager.getClients().forEach(client -> client.sendMessage(message));
    }

    public void broadcastGameState() {
        GameMessage gameStateMessage = new GameMessage("gameState");
        gameStateMessage.addData("currentPlayer",
                new String[] { String.valueOf(model.getCurrentPlayerID()) });
        gameStateMessage.addData("listOfPlayers", model.getListOfPlayers());
        gameStateMessage.addData("topCard", new String[] { model.getTopPlayedCard() });
        for (int player : idsOf(model.getListOfPlayers())) {
            gameStateMessage.addData(String.valueOf(player), model.getPlayerHandById(player));
        }
        broadcastMessage(gameStateMessage);
    }

    public void broadCastNumberOfConnected() {
        GameMessage numConMessage = new GameMessage("nPlayers");
        String num = String.valueOf(connectionManager.numConnected());
        numConMessage.addData("n", new String[] { num });
        broadcastMessage(numConMessage);
    }

    // player data contains name/id and number of cards in hand as
    // this returns array in same order, but only with player names
    private int[] idsOf(String[] players) {
        int[] ids = new int[players.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = idOf(players[i]);
        }
        return ids;
    }

    // player object toString: "Player " + id + " - Cards: " + handSize;
    private int idOf(String player) {
        return Integer.parseInt(player.split(" ")[1]);
    }

    public synchronized void handlePlayCard(int sender, int cardNumber) {
        if (validSender(sender)) {
            model.playCard(cardNumber);
            broadcastGameState();
            // if spelare har vunnit: skicka win message till clients?
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

    // TODO: DESSA?
    @Override
    public void requestPlayers(int lower, int upper) {
        for (ClientManager client : connectionManager.getClients()) {
            model.addPlayer(client.getClientId());
        }
    }

    @Override
    public void announceWinner(int id, int score) {
        GameMessage winMessage = new GameMessage("gameOver", id);
        winMessage.addData("n", new String[] { String.valueOf(score) });
    }

    @Override
    public void requestWildColor() {
        //TODO: current player to choose color
    }

    public void ping() {
        broadcastMessage(new GameMessage("ping"));
    }

    public void pong() {
        System.out.println("pong");
    }

}
