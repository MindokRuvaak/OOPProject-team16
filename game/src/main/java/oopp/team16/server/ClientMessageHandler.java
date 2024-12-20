package oopp.team16.server;

import oopp.team16.controller.GameViewController;

import java.util.Map;
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

            case "id":
                setPlayerId(message.getSender());
                break;
            case "nPlayers":
                viewController.setNumPlayersConnected(readNum(message.getData()));
                break;
            case "ping":
                pong();
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }

    private int readNum(Map<String,String[]> map) {
        return Integer.parseInt(map.get("n")[0]);
    }

    private void handleGameState(GameMessage message) {
        viewController.recieveServerData(message.getData());
        viewController.updateDisplay();
    }

    private void handleGameOver(GameMessage message) {
        int winner = message.getSender();
        int score = readNum(message.getData());
        // score används inte i announcewinner även fast den tar in en score-parameter.
        viewController.announceWinner(winner, score);
    }

    private void handlePlayerDisconnected(GameMessage message) {
        // viewController.disconnectPlayer?
    }

    private void pong() {
        System.out.println("pong");
        viewController.ping();
    }

    private void setPlayerId(int id) {
        viewController.setPlayerId(id);
    }
}
