package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShutdownManager {
    private static final Logger logger = Logger.getLogger(ShutdownManager.class.getName());
    private ServerSocket serverSocket;

    public ShutdownManager(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void shutdown() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                logger.info("Server stopped.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error stopping the server", e);
        }
    }
}
