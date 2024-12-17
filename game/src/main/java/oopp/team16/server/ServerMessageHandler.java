package oopp.team16.server;

import oopp.team16.model.Model;
import java.util.logging.Logger;

public class ServerMessageHandler {
    private static final Logger logger = Logger.getLogger(ServerMessageHandler.class.getName());

    private final GameServer gameServer;
    private final Model model;

    public ServerMessageHandler(GameServer gameServer) {
        this.gameServer = gameServer;
        this.model = gameServer.getModel();
    }

    public void handleMessage(GameMessage message, ClientManager sender) {
        logger.info("Handling message: " + message.getType());
        switch (message.getType()) {

            case "playerMove":
                handlePlayerMove(message, sender);
                break;

            case "endTurn":
                handleEndTurn(sender);
                break;

            default:
                logger.warning("Unknown message type: " + message.getType());
        }
    }

    private void handlePlayerMove(GameMessage message, ClientManager sender) {
        Object cardPlayedObj = message.getData().get("cardPlayed");
        if (cardPlayedObj instanceof Number) {
            int cardPlayed = ((Number) cardPlayedObj).intValue();
            logger.info(sender.getClientName() + " played card: " + cardPlayed);
            model.playCard(cardPlayed);
            gameServer.broadcastGameState();
        } else {
            logger.warning("Invalid 'cardPlayed' data type in message.");
        }
    }

    private void handleEndTurn(ClientManager sender) {
        logger.info("Ending turn for: " + sender.getClientName());
        model.endTurn();
        gameServer.broadcastGameState();
    }
}
