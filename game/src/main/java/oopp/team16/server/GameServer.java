package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    private final int port;
    private final int maxPlayers;

    //TODO: kika vad som bör vara private och public. typ allt är public just nu.
    // lös parsecommand grejen
    // ge gameclient.closeconnection ett hem eller ta bort.
    // fixa namn åt clients
    // vet ej om en messagemanager behövs.
    // finns småkommentarer i olika filer som beskriver mer issues.
    // nu när vi har en rough view är det möjligt att testa mer.

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        logger.info("GameServer initialized.");
    }

    public void startup() {
        StartupManager startupManager = new StartupManager();
        this.serverSocket = startupManager.startServer(port);

        if (serverSocket != null) {
            logger.info("Server is now accepting connections...");
            this.connectionManager = new ConnectionManager(serverSocket, maxPlayers);
            connectionManager.acceptConnections();
        } else {
            logger.severe("Server failed to start.");
        }
    }

    public void broadcastMessage(GameMessage message) {
        List<ClientManager> clients = connectionManager.getClients();
        for (ClientManager client : clients) {
            try {
                client.sendMessageToClient(message);
            } catch (Exception e) {
                logger.warning("Failed to send message to client: " + client.getClientSocket().getInetAddress() + ". Error: " + e.getMessage());
            }
        }
    }


    public void shutdown() {
        logger.info("Shutting down GameServer...");
        if (connectionManager != null) {
            connectionManager.closeConnections();
        } else {
            logger.warning("ConnectionManager is null. Skipping connection cleanup.");
        }

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                logger.info("Server socket closed.");
            }
        } catch (IOException e) {
            logger.severe("Error while closing server socket: " + e.getMessage());
        }

        logger.info("GameServer shutdown completed.");
    }
}
