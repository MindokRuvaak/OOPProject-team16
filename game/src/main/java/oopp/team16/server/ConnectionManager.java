package oopp.team16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectionManager {
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
            while (!serverSocket.isClosed() && clients.size() < maxPlayers) {
                Socket clientSocket = serverSocket.accept();

                ClientManager clientManager = new ClientManager(clientSocket);
                clients.add(clientManager); // Synchronized list ensures thread safety
                new Thread(clientManager).start();

            }

            if (clients.size() >= maxPlayers) {
            }
        } catch (IOException ex) {
            if (serverSocket.isClosed()) {
            } else {
            }
        }
    }

    public void removeClient(ClientManager client) {
        if (clients.remove(client)) {
        } else {
        }
    }

    public void closeConnections() {
        synchronized (clients) {
            for (ClientManager client : new ArrayList<>(clients)) {
                try {
                    client.closeServerConnection();
                    removeClient(client); // Safe removal after cleanup
                } catch (Exception e) {
                }
            }
        }
    }

    public List<ClientManager> getClients() {
        return Collections.unmodifiableList(clients); // Prevents direct modification
    }
}
