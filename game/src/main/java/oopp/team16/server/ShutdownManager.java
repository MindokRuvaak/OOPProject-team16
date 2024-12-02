package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class ShutdownManager {
    private static final Logger logger = Logger.getLogger(ShutdownManager.class.getName());

    public void stopServer(ServerSocket serverSocket) {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                logger.info("Server stopped successfully.");
            } catch (IOException e) {
                logger.severe("Error stopping server: " + e.getMessage());
            }
        } else {
            logger.warning("Server is already stopped or was never started.");
        }
    }
}
