package oopp.team16.server;

import oopp.team16.model.Model;

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

    public GameServer(Model model, int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.startupManager = new StartupManager();
        this.shutdownManager = new ShutdownManager();
        logger.info("GameServer initialized.");
    }

    public void startup() {
        this.serverSocket = startupManager.startServer(port);
        if (serverSocket != null) {
            logger.info("beep");

            this.connectionManager = new ConnectionManager(serverSocket, maxPlayers);
            connectionManager.acceptConnections();
            logger.info("Waiting for connections...");
        } else {
            logger.severe("Server failed to start.");
        }
    }

    //public void shutdown(){}
    public ConnectionManager getConnectionController() {
        return connectionManager;
    }

    public ShutdownManager getShutdownManager() {
        return shutdownManager;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
