package oopp.team16.server;

import oopp.team16.controller.GameViewController;

import java.util.logging.Logger;

public class ClientMessageHandler {
    private static final Logger logger = Logger.getLogger(ClientMessageHandler.class.getName());

    private final GameViewController viewController;

    public ClientMessageHandler(GameViewController viewController) {
        this.viewController = viewController;
    }

    public void handleMessage(GameMessage message) {
        logger.info("Handling message of type: " + message.getType());

        switch (message.getType()) {

            case "gameState":
                handleGameState(message);
                break;

            case "gameOver":
                handleGameOver(message);
                break;

            case "playerDisconnected":
                handlePlayerDisconnected(message);
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }

    //TODO: vad ska uppdateras?
    private void handleGameState(GameMessage message) {
        //viewController.updateGameState();
    }

    private void handleGameOver(GameMessage message) {
        String winner = (String) message.getData().get("winner");
        //viewController.showWinner(winner);
    }

    private void handlePlayerDisconnected(GameMessage message) {
        //viewController.disconnectPlayer?
    }
}
