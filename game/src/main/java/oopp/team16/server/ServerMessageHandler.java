package oopp.team16.server;

import java.util.logging.Logger;

// både denna och clientmessagehandler borde kunna göras bättre. interfaces istället?
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
                handleGameStart(message.getSender());
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
        gameServer.addNonReadyPlayer();
    }

    // får meddelande från play-knappen i lobbyn
    private void handleGameStart(int i) {
        gameServer.setPlayerReady(i);
        gameServer.startGame();
    }

    // får meddelande från drawcard knappen
    private void handleDrawCard(GameMessage message) {
        int sender = message.getSender();
        gameServer.handleDrawCard(sender);
    }

    // får meddelande från playcard-knapp, antagligen då när man trycker på ett kort
    private void handlePlayCard(GameMessage message) {
        String[] cardData = message.getData().get("cardIndex");
        // card given as single value integer, index in player hand
        int cardInd = Integer.parseInt(cardData[0]);
        System.out.println("recieved card index " + cardInd);
        int sender = message.getSender();
        gameServer.handlePlayCard(sender, cardInd);
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

    // får meddelande när klient disconnectar. tar bort player i modellen?
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
