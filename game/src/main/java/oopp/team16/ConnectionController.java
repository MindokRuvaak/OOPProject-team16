package oopp.team16;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionController {
    private static final Logger logger = Logger.getLogger(ConnectionController.class.getName());
    private final ServerSocket serverSocket;
    private final int maxPlayers;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final Model model;

    public ConnectionController(ServerSocket serverSocket, int maxPlayers, Model model) {
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
            } catch (IOException ex) {
                logger.warning("Error accepting connection: " + ex.getMessage());
            }
        }
        logger.info("Max players connected. No longer accepting connections.");
    }

    public synchronized void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }

    public List<ClientHandler> getClients() {
        return new ArrayList<>(clients);
    }

    public void stopConnections() {
        for (ClientHandler client : clients) {
            client.closeConnection();
        }
    }
}
