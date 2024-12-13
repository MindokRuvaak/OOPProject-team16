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
        this.clients = Collections.synchronizedList(new ArrayList<>());
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


    public void removeClient(ClientManager client) {
        synchronized (clients) {
            if (clients.remove(client)) {
                logger.info("Client removed. Current players connected: " + clients.size() + "/" + maxPlayers);
            }
        }
    }

    public void closeConnections() {
        logger.info("Closing all client connections...");
        synchronized (clients) {
            for (ClientManager client : new ArrayList<>(clients)) {
                try {
                    client.closeServerConnection();
                    removeClient(client);
                } catch (Exception e) {
                    logger.warning("Error while closing client connection: " + e.getMessage());
                }
            }
        }
        logger.info("All client connections closed.");
    }

    public List<ClientManager> getClients() {
        return Collections.unmodifiableList(clients); // Prevent modification
    }
}
