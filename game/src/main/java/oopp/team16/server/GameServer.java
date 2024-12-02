package oopp.team16.server;

import oopp.team16.model.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());
    private final ServerSocket serverSocket;
    private final StartupManager startupManager;
    private final ShutdownManager shutdownManager;
    private final int port;

    // Constructor to initialize GameServer
    public GameServer(Model model, int maxPlayers, int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            startupManager = new StartupManager();
            shutdownManager = new ShutdownManager(serverSocket);
            this.port = port;
            logger.info("Server started on port " + port);
        } catch (IOException e) {
            logger.severe("Error starting server on port " + port + ": " + e.getMessage());
            throw e;  // Rethrow to propagate the error
        }
    }

    public void startup() {
        startupManager.startServer(port);
        logger.info("Waiting for connections...");
        //ska mer göras? eller är det allt
        }

    public void stopServer() {
        try {
            shutdownManager.shutdown();
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            logger.info("Server stopped");
        } catch (IOException e) {
            logger.severe("Error during server shutdown: " + e.getMessage());
        }
    }
}
