package oopp.team16.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class StartupManager {
    private static final Logger logger = Logger.getLogger(StartupManager.class.getName());

    public ServerSocket startServer(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0")); //sista två parametrar är kanske inte nödvändiga ? tror inte det iaf
            logger.info("Server started and listening on " +
                serverSocket.getInetAddress().getHostAddress() + ":" + port);
        } catch (IOException e) {
            logger.severe("Error starting server on port " + port + ": " + e.getMessage());
        }
        return serverSocket;
    }
}
