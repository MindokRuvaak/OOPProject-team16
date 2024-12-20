package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

// ConnectionManager hanterar connections, från serversidan
public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    private final ServerSocket serverSocket;
    private final int maxPlayers;
    private final GameServer gameServer;
    private final List<ClientManager> clients;
    private int idCounter = 0;

    public ConnectionManager(ServerSocket serverSocket, int maxPlayers, GameServer gameServer) {
        this.serverSocket = serverSocket;
        this.maxPlayers = maxPlayers;
        this.gameServer = gameServer;
        this.clients = new CopyOnWriteArrayList<>();
    }

    private synchronized int generatePlayerId() {
        return idCounter++;  // vi kan använda UUID också men vet inte om det behövs. good practice kanske
    }

    public void acceptConnections() {
        while (gameServer.isRunning() && clients.size() < maxPlayers) {
            try {
                Socket clientSocket = serverSocket.accept();
                int id = generatePlayerId();
                ClientManager clientManager = new ClientManager(clientSocket, this, gameServer, id);
                clientManager.sendMessage(new GameMessage("id",id));
                System.out.println("player connected with id: " + id);
                clients.add(clientManager);

                logger.info(String.format("Accepted connection from %s. Player %d connected. Total players: %d.",
                    clientSocket.getInetAddress(), id, clients.size()));

                new Thread(clientManager, "ClientManager-" + id).start();

            } catch (IOException e) {
                if (gameServer.isRunning()) {
                    logger.severe("Error accepting connection: " + e.getMessage());
                } else {
                    logger.info("Server socket closed. Stopping acceptConnections.");
                    break;
                }
            }
        }
        if (clients.size() >= maxPlayers) {
            logger.info("Max players connected. No longer accepting connections.");
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

    public List<ClientManager> getClients() {
        return clients;
    }

    public synchronized void removeClient(ClientManager clientManager) {
        clients.remove(clientManager);
        logger.info(String.format("Player %d disconnected. Connection closed. Total players: %d.",
            clientManager.getClientId(), clients.size()));
    }

    public int numConnected() {
        return clients.size();
    }
}
