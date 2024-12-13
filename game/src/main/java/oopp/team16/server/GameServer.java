package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    private final int port;
    private final int maxPlayers;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    public void startup() throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.connectionManager = new ConnectionManager(serverSocket, maxPlayers);
        new Thread(connectionManager::acceptConnections, "ConnectionManagerThread").start();
        logger.info("GameServer started successfully on port " + port);
    }

    public void shutdown() throws IOException {
        connectionManager.closeConnections();
        serverSocket.close();
        logger.info("GameServer shutdown completed.");
    }

    public void broadcastMessage(GameMessage message) {
        List<ClientManager> clients = connectionManager.getClients();
        for (ClientManager client : clients) {
            try {
                client.sendMessage(message);
            } catch (Exception e) {
                logger.warning("Failed to send message to client"  + e.getMessage());
            }
        }
    }
}
