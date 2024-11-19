package oopp.team16.gameserver;

import oopp.team16.model.Model;
import java.util.logging.Logger;

public class GameActionController {
    private static final Logger logger = Logger.getLogger(GameActionController.class.getName());
    private final Model gameModel;
    private final ConnectionController connectionController;

    public GameActionController(Model model, ConnectionController connectionController) {
        this.gameModel = model;
        this.connectionController = connectionController;
    }

    /*public void handleAction(ClientHandler client, String action) {
        logger.info("Handling action: " + action);

        // Update the game state
        gameModel.updateState(action);

        // Notify all connected clients
        List<ClientHandler> clients = connectionController.getClients();
        for (ClientHandler c : clients) {
            c.sendMessage("Game state updated: " + gameModel.getState());
        }
    }*/
}
