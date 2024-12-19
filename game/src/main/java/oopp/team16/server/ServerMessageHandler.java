package oopp.team16.server;

import java.util.logging.Logger;

public class ServerMessageHandler {
    private static final Logger logger = Logger.getLogger(ServerMessageHandler.class.getName());
    private final GameServer gameServer;

    public ServerMessageHandler(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void handleMessage(GameMessage message) {
        logger.info("Handling message: " + message.getType());

        switch (message.getType()) {

            case "gameStart":
                handleGameStart();
                break;

            case "drawCard":
                handleDrawCard(message);
                break;

            case "playCard":
                handlePlayCard(message);
                break;

            case "endTurn":
                handleEndTurn(message);
                break;

            case "gameWin":
                // handleGameWin(sender)?
                break;

            case "playerDisconnect":
                // handlePlayerDisconnect(sender);
                break;

            case "playerConnected":
                handePlayerConnection();
                break;

            case "ping":
                ping();
                break;
            default:
                logger.warning("Unknown message type: " + message.getType());
        }
    }

    // får meddelande från start-game knappen
    private void handleGameStart() {
        gameServer.startGame();
    }

    // får meddelande från drawcard knappen
    private void handleDrawCard(GameMessage message) {
        int sender = message.getSender();
        gameServer.handleDrawCard(sender);
    }

    // får meddelande från playcard-knapp, antagligen då när man trycker på ett
    // kort.
    private void handlePlayCard(GameMessage message) {
        Object cardData = message.getData().get("cardPlayed");
        int sender = message.getSender();

        if (cardData instanceof Number) { // Gson gör ibland int till double.
            int cardNumber = ((Number) cardData).intValue();
            gameServer.handlePlayCard(sender, cardNumber);
        }
    }

    // får meddelande från endturn knappen
    private void handleEndTurn(GameMessage message) {
        int sender = message.getSender();
        gameServer.handleEndTurn(sender);
    }

    private void handleGameWin(GameMessage message) {
        // gameServer.handleGameWin();
    }

    private void handlePlayerDisconnect(GameMessage message) {
        // gameServer.handlePlayerDisconnect();
    }

    private void ping() {
        gameServer.pong();
    }
    

    private void handePlayerConnection() {
        gameServer.provideClientId();
    }

}
