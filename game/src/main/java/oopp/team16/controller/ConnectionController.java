package oopp.team16.controller;

import oopp.team16.model.Model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionController {
    private static final Logger logger = Logger.getLogger(ConnectionController.class.getName());
    private final ServerSocket serverSocket;
    private int maxPlayers;
    List<ClientHandler> clients = new CopyOnWriteArrayList<>(); //CopyOnWriteArrayList allows for safe concurrent access, avoiding potential deadlocks and simplifying the code. It is suitable when the list is mostly read and modified infrequently (e.g., adding/removing clients).
    private final Model model;

    public ConnectionController(ServerSocket serverSocket, int maxPlayers, Model model) throws IOException {
        this.serverSocket = serverSocket;
        this.maxPlayers = maxPlayers;
        this.model = model;

    }

    public void acceptConnections() {
        while (clients.size() < maxPlayers) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                synchronized (clients) {
                    clients.add(clientHandler);
                }
                new Thread(clientHandler).start();

                logger.info("Current players connected: " + clients.size() + "/" + maxPlayers);
            } catch (SocketTimeoutException ex) {
                logger.info("Waiting for connections timed out. Retrying...");
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Error accepting connection", ex);
            }
        }
        logger.info("Max players connected. No longer accepting connections.");
    }

    public synchronized void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
        }
        logger.info("Client removed. Current players connected: " + clients.size() + "/" + maxPlayers);
    }

    public synchronized void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        logger.info("Max players limit updated to: " + maxPlayers);
    }

    public List<ClientHandler> getClients() {
        return new ArrayList<>(clients);
    }

    public void stopConnections() {
        for (ClientHandler client : clients) {
            client.closeConnection();
        }
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
                logger.info("Server socket closed successfully.");
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error closing server socket", ex);
        }
    }
}

