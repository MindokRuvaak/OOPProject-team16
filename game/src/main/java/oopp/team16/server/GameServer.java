package oopp.team16.server;

import oopp.team16.model.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
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
        try {
            this.serverSocket = new ServerSocket(port);
            this.model = new Model();
            this.connectionManager = new ConnectionManager(serverSocket, maxPlayers, this);
            this.running = true;

            new Thread(connectionManager::acceptConnections, "ConnectionManagerThread").start();
            logger.info("GameServer started successfully on port " + port);
        } catch (Exception e) {
            logger.severe("Error starting the GameServer: " + e.getMessage());
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
            logger.warning("Error shutting down the server: " + e.getMessage());
        }
    }

    public void startGame() {

        if (model.getPlayers().isEmpty()) {
            logger.severe("Cannot start game: No players have been added.");
            throw new IllegalStateException("No players in the game.");
        }
        model.initGame();
        model.startGame();
        broadcastGameState();
    }


    public void processClientMessage(GameMessage message, ClientManager sender) {
        logger.info("Processing message of type: " + message.getType());

        try {
            switch (message.getType()) {
                case "playerMove":
                    handlePlayerMove(message, sender);
                    break;

                case "endTurn":
                    handleEndTurn(message, sender);
                    break;

                case "chatMessage":
                    handleChatMessage(message);
                    break;

                default:
                    logger.warning("Unknown message type received: " + message.getType());
            }
        } catch (Exception e) {
            logger.severe("Error processing client message: " + e.getMessage());
        }
    }

    private void handleEndTurn(GameMessage message, ClientManager sender) {
        logger.info("Ending turn for: " + message.getSender());

        model.endTurn();

        broadcastGameState();
    }

    private void handlePlayerMove(GameMessage message, ClientManager sender) {
        Object cardPlayedObj = message.getData().get("cardPlayed");

        int cardPlayed = 0;
        if (cardPlayedObj instanceof Number) {
            cardPlayed = ((Number) cardPlayedObj).intValue();
        } else {
            logger.warning("Invalid cardPlayed data type.");
            return;
        }

        logger.info(sender.getClientName() + " played card: " + cardPlayed);
        model.playCard(cardPlayed);
        broadcastGameState();
    }



    private void handleChatMessage(GameMessage message) {
        connectionManager.getClients().forEach(client -> client.sendMessage(message));
    }

    private void broadcastGameState() {
        GameMessage gameStateMessage = new GameMessage("gameState");
        gameStateMessage.addData("topCard", model.getTopPlayedCard());
        gameStateMessage.addData("currentPlayer", model.getCurrentPlayerID());

        // Collect hand sizes
        Map<String, Integer> hands = new HashMap<>();
        for (String player : model.getListOfPlayers()) {
            hands.put(player, model.getPlayerHandSize(player));
        }
        gameStateMessage.addData("hands", hands);

        broadcastMessage(gameStateMessage);
    }


    public void broadcastMessage(GameMessage message) {
        logger.info("Broadcasting message to all connected clients: " + message);
        for (ClientManager client : connectionManager.getClients()) {
            client.sendMessage(message);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Model getModel() {
        return model;
    }

}
