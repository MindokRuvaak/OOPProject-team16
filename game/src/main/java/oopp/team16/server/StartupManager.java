package oopp.team16.server;

import oopp.team16.controller.GameActionController;
import oopp.team16.model.Model;
import java.io.IOException;
import java.util.logging.Logger;

public class StartupManager {
    private static final Logger logger = Logger.getLogger(StartupManager.class.getName());

    private GameServer gameServer;
    private ConnectionController connectionController;
    private GameActionController gameActionController;

    public StartupManager(Model model, int maxPlayers, int port) throws IOException {
        // Initialize and start the server
        gameServer = new GameServer(model, maxPlayers, port);
        connectionController = gameServer.getConnectionController();
        gameActionController = gameServer.getGameActionController();
    }

    public void startServer() {
        logger.info("Starting the server...");
        gameServer.acceptConnections();
    }

    // Getter method to access the GameServer instance
    public GameServer getGameServer() {
        return gameServer;
    }
}
