package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    private final ServerSocket serverSocket;
    private final List<ClientManager> clients;
    private final int maxPlayers;
    private final GameServer gameServer; // Reference to GameServer

    public ConnectionManager(ServerSocket serverSocket, int maxPlayers, GameServer gameServer) {
        this.serverSocket = serverSocket;
        this.maxPlayers = maxPlayers;
        this.clients = new CopyOnWriteArrayList<>();
        this.gameServer = gameServer;
    }

    public void acceptConnections() {
        try {
            while (clients.size() < maxPlayers) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                String playerName = "Player" + (clients.size() + 1); // Unique player name
                gameServer.getModel().addPlayer(playerName); // Add player to the Model

                ClientManager clientManager = new ClientManager(clientSocket, gameServer, playerName);
                clients.add(clientManager);
                new Thread(clientManager, "ClientManager-" + clients.size()).start();

                logger.info("Current players connected: " + clients.size() + "/" + maxPlayers);
            }

            logger.info("Max players connected. Starting the game...");
            gameServer.startGame();
        } catch (IOException e) {
            logger.warning("Error accepting connection: " + e.getMessage());
        }
    }



    public void removeClient(ClientManager client) {
        if (clients.remove(client)) {
            logger.info("Client removed. Current players connected: " + clients.size() + "/" + maxPlayers);
        }
    }

    public void closeConnections() {
        for (ClientManager client : clients) {
            client.closeServerConnection();
        }
        clients.clear();
        logger.info("All client connections closed.");
    }

    public List<ClientManager> getClients() {
        return Collections.unmodifiableList(clients); // Prevent external modification
    }
}
