package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    private final ServerSocket serverSocket;
    private final List<ClientManager> clients;
    private final int maxPlayers;
    private final GameServer gameServer;

    public ConnectionManager(ServerSocket serverSocket, int maxPlayers, GameServer gameServer) {
        this.serverSocket = serverSocket;
        this.maxPlayers = maxPlayers;
        this.gameServer = gameServer;
        this.clients = new CopyOnWriteArrayList<>();
    }

    public void acceptConnections() {
        try {
            while (gameServer.isRunning()) {
                if (clients.size() >= maxPlayers) {
                    logger.info("Max players reached. Waiting for open slots...");
                    Thread.sleep(1000); // Prevent tight looping
                    continue;
                }
                try {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("Accepted connection from: " + clientSocket.getInetAddress());

                    int id = generatePlayerId();
                    ClientManager clientManager = new ClientManager(clientSocket, gameServer, id);
                    clients.add(clientManager);

                    logger.info("Player " + id + " connected. Total players: " + clients.size());
                    clientManager.run();

                } catch (IOException e) {
                    if (gameServer.isRunning()) {
                        logger.severe("Error accepting connection: " + e.getMessage());
                    } else {
                        logger.info("Server socket closed. Stopping acceptConnections.");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Unhandled exception in acceptConnections: " + e.getMessage());
        } finally {
            logger.info("acceptConnections has stopped.");
        }
    }


    public void removeClient(ClientManager client) {
        if (clients.remove(client)) {
            logger.info("Removed client: " + client.getClientId());
            /*gameServer.broadcastMessage(new GameMessage("playerDisconnected")
                .addData("playerId", client.getClientId()));*/
        }
    }

    public void closeConnections() {
        logger.info("Closing all client connections...");
        for (ClientManager client : clients) {
            client.closeConnection();
        }
        clients.clear();
        logger.info("All client connections closed.");
    }

    private int generatePlayerId() {
        return (clients.size() + 1);
    }

    public List<ClientManager> getClients() {
        return clients;
    }
}
