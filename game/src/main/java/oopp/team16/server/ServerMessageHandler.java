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
    private void handlePlayerMove(GameMessage message) {
        Object cardData = message.getData().get("cardPlayed");
        Object cardColor = message.getData().get("color");
        String playerName = message.getSender();

        if (cardData instanceof Number) { //Gson gör ibland saker till double?
            int cardNumber = ((Number) cardData).intValue();
            String cardColorString = (String) cardColor;
            //gameServer.handlePlayerMove(playerName, cardNumber, cardColorString); uppdatera så att playermove tar number och string?

        } else if (cardData instanceof String) {
            String wildCardType = (String) cardData;
            String wildCardColor = cardColor instanceof String ? (String) cardColor : "black"; //svart default
            //gameServer.handleSpecialCard(playerName, wildCardType, wildCardColor);

        } else {
            logger.warning("Invalid 'cardPlayed' data type in message: " + cardData);
        }
    }

    //detta ska skickas från drawcard knappen
    private void handleDrawCard(GameMessage message) {
        String sender = message.getSender();
        gameServer.handleDrawCard(sender);
    }

    //detta ska skickas från endturn knappen
    private void handleEndTurn(GameMessage message) {
        String sender = message.getSender();
        gameServer.handleEndTurn(sender);
    }
}
