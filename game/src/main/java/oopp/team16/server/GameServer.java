package oopp.team16.server;

import java.net.ServerSocket;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());
    private ServerSocket serverSocket;
    private final StartupManager startupManager;
    private final ShutdownManager shutdownManager;
    private ConnectionManager connectionManager;
    private final int port;
    private final int maxPlayers;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.startupManager = new StartupManager();
        this.shutdownManager = new ShutdownManager();
        logger.info("GameServer initialized.");
    }

    public void startup() {
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
            connectionManager.closeConnections(); // Gracefully close and remove all clients
        } else {
            logger.warning("ConnectionManager is null. Skipping connection cleanup.");
        }

        if (shutdownManager != null) {
            shutdownManager.stopServer(serverSocket); // Stop the server
        } else {
            logger.warning("ShutdownManager is null. Server may not stop cleanly.");
        }

        logger.info("GameServer shutdown completed.");
    }
}
