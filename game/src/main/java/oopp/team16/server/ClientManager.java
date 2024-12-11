package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;

public class ClientManager extends MessageHandler implements Runnable {
    private final Socket clientSocket;
    private volatile boolean running = true;

    public ClientManager(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            initializeStreams(clientSocket.getInputStream(), clientSocket.getOutputStream());
            listenForMessages();
        } catch (IOException e) {
        } finally {
            closeServerConnection();
        }
    }

    private void listenForMessages() {
        while (running) {
            GameMessage message = receiveMessage();
            if (message == null) {
                break;
            }
            processClientMessage(message);
        }
    }

    private void processClientMessage(GameMessage message) {
        if (message == null || message.getType() == null) {
            return;
        }

        // Add your message-specific handling here
    }

    public void stopListening() {
        running = false;
    }

    public void closeServerConnection() {
        stopListening(); // Stop the listener thread
        closeStreams();
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close(); // Close the socket
            }
        } catch (IOException e) {
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
