package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class StartupManager {
    private static final Logger logger = Logger.getLogger(StartupManager.class.getName());
    private ServerSocket serverSocket;

    public ServerSocket startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000); // Set timeout if needed
            logger.info("Server started and listening on port " + port);
        } catch (IOException e) {
            logger.severe("Error starting server: " + e.getMessage());
        }
        return serverSocket;
    }
}
