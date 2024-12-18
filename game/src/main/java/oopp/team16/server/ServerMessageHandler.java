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

            case "playerMove":
                handlePlayerMove(message);
                break;

            case "endTurn":
                handleEndTurn(message);
                break;

            case "gameWin":
                //handleGameWin(sender)?
                break;

            default:
                logger.warning("Unknown message type: " + message.getType());
        }
    }

    //detta ska skickas från start-game knappen
    private void handleGameStart() {
        gameServer.startGame();
    }

    //detta ska skickas från playcard-knapp, antagligen då när man trycker på ett kort.
    //TODO: Special cards är inte numeric
    private void handlePlayerMove(GameMessage message) {

        Object cardPlayedObj = message.getData().get("cardPlayed");
        String sender = message.getSender();

        if (cardPlayedObj instanceof Number) { //Gson gör ibland saker till double?
            int cardPlayed = ((Number) cardPlayedObj).intValue();
            gameServer.handlePlayerMove(sender, cardPlayed);
        } else if (cardPlayedObj instanceof String) {
            String specialCard = (String) cardPlayedObj;
            //gameServer.handleSpecialCard(specialCard); // Add this method to GameServer
        } else {
            logger.warning("Invalid 'cardPlayed' data type in message: " + cardPlayedObj);
        }
    }

    //detta ska skickas från drawcard knappen
    private void handleDrawCard(GameMessage message) {
        String sender = message.getSender();
        //map på card details
        gameServer.handleDrawCard(sender);
    }

    //detta ska skickas från endturn knappen
    private void handleEndTurn(GameMessage message) {
        String sender = message.getSender();
        gameServer.handleEndTurn(sender);
    }
}
