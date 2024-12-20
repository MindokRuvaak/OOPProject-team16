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

            case "sayUno":
                handleSayUno(message);
                break;

            case "gameWin":
                handleGameWin(message);
                break;
            case "playerDisconnect":
                handlePlayerDisconnect(message);
                break;
            case "playerConnected":
                handlePlayerConnect();
                break;
            case "ping":
                ping();
                break;
            default:
                logger.warning("Unknown message type: " + message.getType());
        }
    }

    private void handlePlayerConnect() {
        gameServer.broadCastNumberOfConnected();
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
    // kort. denna
    private void handlePlayCard(GameMessage message) {
        Object cardData = message.getData().get("cardPlayed");
        int sender = message.getSender();
        gameServer.handlePlayCard(sender, 3); // TODO: HandlePlayCard i server ska inte ta emot

    }

    // får meddelande från endturn knappen
    private void handleEndTurn(GameMessage message) {
        int sender = message.getSender();
        gameServer.handleEndTurn(sender);
    }

    // får meddelande från uno knappen
    private void handleSayUno(GameMessage message) {
        // gameServer.handleSayUno
    }

    // får meddelande när en klient når 0 kort och sagt uno
    private void handleGameWin(GameMessage message) {
        // gameServer.handleGameWin();
    }

    // får meddelande när klient disconnectar. ta bort player i modellen.
    private void handlePlayerDisconnect(GameMessage message) {
        // gameServer.handlePlayerDisconnect();
    }

    private void ping() {
        gameServer.pong();
    }

    private void handePlayerConnection() {
        // gameServer.getClientId();
    }

}
