package oopp.team16.server;

import oopp.team16.controller.GameViewController;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    private GameClient gameClient;
    private final ClientMessageHandler messageHandler;

    public GameClientController(GameViewController viewController) {
        this.messageHandler = new ClientMessageHandler(viewController);
    }

    //kopplas till join-knappen
    public void connect(String serverAddress, int port) {
        try {
            gameClient = new GameClient(serverAddress, port);
            gameClient.startListening(messageHandler);
            logger.info("Connected to server and started listening for messages.");
        } catch (RuntimeException e) {
            logger.severe("Failed to connect: " + e.getMessage());
            throw e;
        }
    }

    //kopplas till shutdownknappen för client. denna behöver ha en gameclient i sig? eller kanske inte. idk.
    public void disconnect() {
        if (gameClient != null && gameClient.isConnected()) {
            gameClient.closeClientConnection();
            logger.info("Disconnected from the server.");
        }
    }

    //TODO: FIXA SENDER!!!
    public void playCard(int cardId, String color) {
        GameMessage playCardMessage = new GameMessage("playCard");
        playCardMessage.addData("cardNumber", cardId); // Example data
        playCardMessage.addData("color", color);
        gameClient.sendMessage(playCardMessage);
    }

    public void endTurn() {
        GameMessage message = new GameMessage("endTurn");
        message.setSender(0);
        gameClient.sendMessage(message);
    }
}
