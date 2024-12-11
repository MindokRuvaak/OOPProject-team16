package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class GameServer {

    private final int port;
    private final int maxPlayers;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    public void startup() {
        StartupManager startupManager = new StartupManager();
        this.serverSocket = startupManager.startServer(port);

        if (serverSocket != null) {
            this.connectionManager = new ConnectionManager(serverSocket, maxPlayers);
            connectionManager.acceptConnections();
        } else {
        }
    }

    public void shutdown() {
        if (connectionManager != null) {
            connectionManager.closeConnections();
        } else {
        }

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
        }

    }

    public void broadcastMessage(GameMessage message) {
        List<ClientManager> clients = connectionManager.getClients();
        for (ClientManager client : clients) {
            try {
                client.sendMessage(message);
            } catch (Exception e) {
            }
        }
    }
}
