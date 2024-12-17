package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    private final ServerSocket serverSocket;
    private final List<ClientManager> clients;
    private final int maxPlayers;
    private final GameServer gameServer;
    private final ExecutorService clientThreadPool;

    public ConnectionManager(ServerSocket serverSocket, int maxPlayers, GameServer gameServer) {
        this.serverSocket = serverSocket;
        this.maxPlayers = maxPlayers;
        this.gameServer = gameServer;
        this.clients = new CopyOnWriteArrayList<>();
        this.clientThreadPool = Executors.newFixedThreadPool(maxPlayers);
    }

    public void acceptConnections() {
        try {
            while (clients.size() < maxPlayers) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Accepted connection from: " + clientSocket.getInetAddress());

                String playerName = generatePlayerName();
                gameServer.getModel().addPlayer(playerName);

                ClientManager clientManager = new ClientManager(clientSocket, gameServer, playerName);
                clients.add(clientManager);
                clientThreadPool.submit(clientManager);

                logger.info("Player " + playerName + " connected. Total players: " + clients.size());
            }

            logger.info("Max players reached. Notifying GameServer to start the game.");
            gameServer.startGame();

        } catch (IOException e) {
            logger.severe("Error accepting connections: " + e.getMessage());
        }
    }

    public void removeClient(ClientManager client) {
        if (clients.remove(client)) {
            logger.info("Removed client: " + client.getClientName());
        }
    }

    public void closeConnections() {
        for (ClientManager client : clients) {
            client.closeConnection();
        }
        clients.clear();
        clientThreadPool.shutdown();
        logger.info("All client connections closed.");
    }

    private String generatePlayerName() {
        return "Player" + (clients.size() + 1);
    }

    public List<ClientManager> getClients() {
        return clients;
    }
}
