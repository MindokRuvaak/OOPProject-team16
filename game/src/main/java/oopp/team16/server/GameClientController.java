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
            this.gameClient = new GameClient(serverAddress, port);
            this.gameClient.startListening(messageHandler);
            logger.info("Connected to server and started listening for messages.");
            connected();
        } catch (RuntimeException e) {
            logger.severe("Failed to connect: " + e.getMessage());
            throw e;
        }
    }

    // kopplas till shutdown-knappen för client.
    public void disconnect() {
        if (this.gameClient != null && this.gameClient.isConnected()) {
            this.gameClient.closeClientConnection();
            playerDisconnect();
            logger.info("Disconnected from the server.");
        }
    }
    // TODO: FIXA SENDER FÖR ALLA DE HÄR ?
    public void gameStart() {
        gameClient.sendMessage(new GameMessage("gameStart"));
    }

    public void drawCard() {
        gameClient.sendMessage(new GameMessage("drawCard"));
    }

    public void playCard(int cardId, String color) {
        GameMessage playCardMessage = new GameMessage("playCard");
        playCardMessage.addData("cardNumber", new String[] { String.valueOf(cardId) }); // Example data
        playCardMessage.addData("color", new String[] { color });
        this.gameClient.sendMessage(playCardMessage);
    }

    public void endTurn() {
        GameMessage message = new GameMessage("endTurn");
        message.setSender(0);
        this.gameClient.sendMessage(message);
    }

    public void pressedStart() {
        this.gameClient.sendMessage(new GameMessage("gameStart"));
    }

    public void sayUno() {
        this.gameClient.sendMessage(new GameMessage("sayUno"));
    }

    private void connected() {
        this.gameClient.sendMessage(new GameMessage("playerConnected"));
    }

    public void playerDisconnect() {
        gameClient.sendMessage(new GameMessage("playerDisconnect"));
    }

    public void ping() {
        // System.out.println(gameClient == null);
        this.gameClient.sendMessage(new GameMessage("ping"));
    }

}
