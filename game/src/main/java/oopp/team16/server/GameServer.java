package oopp.team16.server;

import java.net.ServerSocket;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    private final int port;
    private final int maxPlayers;

    //TODO: kika vad som bör vara private och public. typ allt är public just nu.

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        logger.info("GameServer initialized.");
    }

    public void startup() {
        StartupManager startupManager = new StartupManager();
        this.serverSocket = startupManager.startServer(port);

        if (serverSocket != null) {
            logger.info("Server is now accepting connections...");
            this.connectionManager = new ConnectionManager(serverSocket, maxPlayers);
            connectionManager.acceptConnections();
        } else {
            logger.severe("Server failed to start.");
        }
    }


    public void shutdown() {
        if (connectionManager != null) {
            connectionManager.closeConnections();
        } else {
            logger.warning("ConnectionManager is null. Skipping connection cleanup.");  // detta bör ändras till ett bättre meddelande.
        }

        ShutdownManager shutdownManager = new ShutdownManager();
        shutdownManager.stopServer(serverSocket);

        logger.info("GameServer shutdown completed.");
    }
}
