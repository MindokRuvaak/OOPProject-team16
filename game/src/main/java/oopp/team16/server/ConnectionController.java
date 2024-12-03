package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionController {
    private static final Logger logger = Logger.getLogger(ConnectionController.class.getName());
    private ServerSocket serverSocket;
    private final List<ClientManager> clients;
    private int maxPlayers;

    public ConnectionController(ServerSocket serverSocket, int maxPlayers) {
        this.serverSocket = serverSocket;
        this.clients = new ArrayList<>();
        this.maxPlayers = maxPlayers;
    }

    public void acceptConnections() {
        while (clients.size() < maxPlayers) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                ClientManager clientHandler = new ClientManager(clientSocket, this);
                synchronized (clients) {
                    clients.add(clientHandler);
                }
                new Thread(clientHandler).start();

                logger.info("Current players connected: " + clients.size() + "/" + maxPlayers);
            } catch (SocketTimeoutException ex) {
                logger.info("Waiting for connections timed out. Retrying...");
            } catch (IOException ex) {
                logger.warning("Error accepting connection");
                logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            }
        }
        logger.info("Max players connected. No longer accepting connections.");
    }

    public void closeConnections() {
        for (ClientManager client : clients) {
            client.closeConnection();
        }
        logger.info("All connections closed.");
    }

}

