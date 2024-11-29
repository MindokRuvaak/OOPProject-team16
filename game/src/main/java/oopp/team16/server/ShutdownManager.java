package oopp.team16.server;

import java.util.logging.Logger;

public class ShutdownManager {
    private static final Logger logger = Logger.getLogger(ShutdownManager.class.getName());

    private GameServer gameServer;

    public ShutdownManager(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void stopServer() {
        logger.info("Stopping the server...");
        gameServer.stopServer();
    }
}
