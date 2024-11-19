package oopp.team16.controller;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private final Socket clientSocket;
    private final ConnectionController connectionController;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, ConnectionController connectionController) {
        this.clientSocket = socket;
        this.connectionController = connectionController;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                logger.info("Received message from client: " + message);

                // Example of handling an action
                //connectionController.getGameActionController().handleAction(this, message);
            }
        } catch (IOException ex) {
            logger.warning("Client disconnected: " + ex.getMessage());
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public void closeConnection() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            connectionController.removeClient(this);
        } catch (IOException ex) {
            logger.warning("Error closing client connection: " + ex.getMessage());
        }
    }
}
