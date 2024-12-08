package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    private final ServerSocket serverSocket;
    private final List<ClientManager> clients;
    private final int maxPlayers;

    public ConnectionManager(ServerSocket serverSocket, int maxPlayers) {
        this.serverSocket = serverSocket;
        this.maxPlayers = maxPlayers;
        this.clients = new ArrayList<>();
    }

    public void acceptConnections() {
        try {
            while (clients.size() < maxPlayers) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                ClientManager clientManager = new ClientManager(clientSocket);
                synchronized (clients) {
                    clients.add(clientManager);
                }
                new Thread(clientManager).start();

                logger.info("Current players connected: " + clients.size() + "/" + maxPlayers);
            }
            logger.info("Max players connected. No longer accepting connections.");
        } catch (IOException ex) {
            logger.warning("Error accepting connection: " + ex.getMessage());
        }
    }

    private void removeClient(ClientManager client) {
        synchronized (clients) {
            if (clients.remove(client)) {
                logger.info("Client removed. Current players connected: " + clients.size() + "/" + maxPlayers);
            } else {
                logger.info(client.getClientSocket().getInetAddress().getHostAddress() + " already removed or not found.");
            }
        }
    }

    public void closeConnections() {
        synchronized (clients) {
            List<ClientManager> clientsCopy = new ArrayList<>(clients);

            for (ClientManager client : clientsCopy) {
                if (!client.getClientSocket().isClosed()) {
                    client.closeConnection();
                }
                removeClient(client); // Safe removal
            }
            logger.info("All connections closed and clients removed.");
        }
    }
    public List<ClientManager> getClients() {
        return Collections.unmodifiableList(clients); // Prevents direct modification
    }

}

