package oopp.team16.server;

import oopp.team16.controller.GameViewController;
import java.util.logging.Logger;

public class GameClientController {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    private GameClient gameClient;
    private final ClientMessageHandler messageHandler;
    private final GameViewController viewController;

    public GameClientController(GameViewController viewController) {
        this.viewController = viewController;
        this.messageHandler = new ClientMessageHandler(this.viewController);
        this.viewController.addClientController(this);
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

    public void drawCard() {
        gameClient.sendMessage(new GameMessage("drawCard", viewController.getPlayerId()));
    }

    public void playCard(int cardInd) {
        System.out.println("playing card at index " + cardInd);
        GameMessage playCardMessage = new GameMessage("playCard", viewController.getPlayerId());
        playCardMessage.addData("cardIndex", new String[] { String.valueOf(cardInd) });
        this.gameClient.sendMessage(playCardMessage);
    }

    public void endTurn() {
        GameMessage message = new GameMessage("endTurn", viewController.getPlayerId());
        this.gameClient.sendMessage(message);
    }

    public void pressedStart() {
        this.gameClient.sendMessage(new GameMessage("gameStart", viewController.getPlayerId()));
    }

    public void sayUno() {
        this.gameClient.sendMessage(new GameMessage("sayUno", viewController.getPlayerId()));
    }

    private void connected() {
        this.gameClient.sendMessage(new GameMessage("playerConnected", viewController.getPlayerId()));
    }

    public void playerDisconnect() {
        gameClient.sendMessage(new GameMessage("playerDisconnect", viewController.getPlayerId()));
    }

    public void ping() {
        // System.out.println(gameClient == null);
        this.gameClient.sendMessage(new GameMessage("ping", viewController.getPlayerId()));
    }

    public void provideRed(){
        this.gameClient.sendMessage(new GameMessage("red", viewController.getPlayerId()));
    }
    public void provideGreen(){
        this.gameClient.sendMessage(new GameMessage("green") );
    }
    public void provideYellow(){
        this.gameClient.sendMessage(new GameMessage("yellow") );
    }
    public void provideBlue(){
        this.gameClient.sendMessage(new GameMessage("blue") );
    }

}
