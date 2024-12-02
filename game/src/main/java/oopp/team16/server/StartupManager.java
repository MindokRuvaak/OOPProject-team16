package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class StartupManager {
    private static final Logger logger = Logger.getLogger(StartupManager.class.getName());

    public ServerSocket startServer(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started and listening on port " + port);
        } catch (IOException e) {
            logger.severe("Error starting server: " + e.getMessage());
        }
        return serverSocket;
    }
}
