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

            case "invalid_action":
                handleInvalidAction(message);
                break;

            default:
                logger.warning("Unknown message type received: " + message.getType());
        }
    }

    private void handleGameState(GameMessage message) {
        String topCard = (String) message.getData().get("topCard");
        String currentPlayer = (String) message.getData().get("currentPlayer");

        @SuppressWarnings("unchecked")
        Map<String, Double> rawHands = (Map<String, Double>) message.getData().get("hands");

        // Convert hand sizes to integers
        Map<String, Integer> hands = convertHandSizes(rawHands);

        logger.info("Received game state update. Top card: " + topCard + ", Current player: " + currentPlayer);
        //viewController.updateGameState(topCard, currentPlayer, hands);
    }


    private void handleGameOver(GameMessage message) {
        String winner = (String) message.getData().get("winner");
        logger.info("Game over. Winner: " + winner);
        //viewController.showWinner(winner);
    }

    private void handleInvalidAction(GameMessage message) {
        String reason = (String) message.getData().get("reason");
        logger.warning("Invalid action received: " + reason);
        //viewController.displayError("Invalid Action: " + reason);
    }


    private void handleChatMessage(GameMessage message) {
        String sender = message.getSender();
        String chatContent = (String) message.getData().get("message");

        logger.info("Chat message received from " + sender + ": " + chatContent);
        //viewController.displayChatMessage(sender, chatContent);
    }


    private Map<String, Integer> convertHandSizes(Map<String, Double> rawHands) {
        Map<String, Integer> hands = new java.util.HashMap<>();
        for (Map.Entry<String, Double> entry : rawHands.entrySet()) {
            hands.put(entry.getKey(), entry.getValue().intValue());
        }
        return hands;
    }
}
