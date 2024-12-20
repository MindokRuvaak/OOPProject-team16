package oopp.team16.server;

import oopp.team16.controller.GameViewController;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    private GameClient gameClient;
    private final ClientMessageHandler messageHandler;

    public GameClientController(GameViewController viewController) {
        this.messageHandler = new ClientMessageHandler(viewController);
        viewController.addClientController(this);
    }

    // kopplas till join-knappen
    public void connect(String serverAddress, int port) {
        try {
            gameClient = new GameClient(serverAddress, port);
            gameClient.startListening(messageHandler);
            logger.info("Connected to server and started listening for messages.");
            // System.out.println(gameClient == null);
        } catch (RuntimeException e) {
            logger.severe("Failed to connect: " + e.getMessage());
            throw e;
        }
    }

    // kopplas till shutdownknappen för client. denna behöver ha en gameclient i
    // sig? eller kanske inte. idk.
    public void disconnect() {
        if (gameClient != null && gameClient.isConnected()) {
            gameClient.closeClientConnection();
            logger.info("Disconnected from the server.");
        }
    }

    // TODO: FIXA SENDER!!!
    public void playCard(int cardId, String color) {
        GameMessage playCardMessage = new GameMessage("playCard");
        playCardMessage.addData("cardNumber", new String[] {String.valueOf(cardId)}); // Example data
        playCardMessage.addData("color", new String[] {color});
        gameClient.sendMessage(playCardMessage);
    }

    public void endTurn() {
        GameMessage message = new GameMessage("endTurn");
        message.setSender(0);
        gameClient.sendMessage(message);
    }

    public void pressedStart() {
        gameClient.sendMessage(new GameMessage("start"));
    }

    public void sayUno(){
        gameClient.sendMessage(new GameMessage("sayUno"));
    }

    public void ping() {
        // System.out.println(gameClient == null);
        gameClient.sendMessage(new GameMessage("ping"));
    }


}
