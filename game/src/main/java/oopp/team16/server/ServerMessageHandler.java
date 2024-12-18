package oopp.team16.server;

import java.util.logging.Logger;

public class ServerMessageHandler {
    private static final Logger logger = Logger.getLogger(ServerMessageHandler.class.getName());
    private final GameServer gameServer;

    public ServerMessageHandler(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void handleMessage(GameMessage message, ClientManager sender) {
        logger.info("Handling message: " + message.getType());

        if (message.getData() == null) {
            logger.warning("Received message with no data: " + message.getType());
            return;
        }

        switch (message.getType()) {
            case "playerMove":
                handlePlayerMove(message, sender);
                break;

            case "endTurn":
                handleEndTurn(sender);
                break;

            case "gameStart":
                handleGameStart(sender);
                break;

            default:
                logger.warning("Unknown message type: " + message.getType());
        }
    }

    //TODO: Special cards är inte numeric
    private void handlePlayerMove(GameMessage message, ClientManager sender) {
        Object cardPlayedObj = message.getData().get("cardPlayed");

        if (cardPlayedObj instanceof Number) { //Gson gör ibland saker till double?
            int cardPlayed = ((Number) cardPlayedObj).intValue();
            logger.info("Player " + sender.getClientId() + " played card: " + cardPlayed);
        } else if (cardPlayedObj instanceof String) {
            String specialCard = (String) cardPlayedObj;
            logger.info("Player " + sender.getClientId() + " played special card: " + specialCard);
            //gameServer.handleSpecialCard(sender, specialCard); // Add this method to GameServer
        } else {
            logger.warning("Invalid 'cardPlayed' data type in message: " + cardPlayedObj);
        }
    }

    private void handleEndTurn(ClientManager sender) {
        logger.info("Ending turn for player: " + sender.getClientId());
        gameServer.handleEndTurn(sender);
    }

    private void handleGameStart(ClientManager sender) {
        logger.info("Player " + sender.getClientId() + " initiated game start.");
        gameServer.startGame();
    }
}
