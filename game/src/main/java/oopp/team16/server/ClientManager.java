package oopp.team16.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    public ClientManager(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // ska denna vara ett field tsm med printwriter? idk

            String message;
            while ((message = in.readLine()) != null) {
                logger.info("Received message from client: " + message);
            }
        } catch (IOException ex) {
            if ("Socket closed".equals(ex.getMessage())) return; // Skip logging for "Socket closed"
            logger.warning("Client disconnected or error occurred: " + ex.getMessage());
        }
    }

        public void closeConnection() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                logger.info("Closing connection for client: " + clientSocket.getInetAddress().getHostAddress());
            }
        } catch (IOException ex) {
            logger.warning("Error closing client connection: " + ex.getMessage());
        }
    }

    public void sendMessageToClient(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        } else {
            logger.warning("Output stream is null for client.");
        }
    }

    public Socket getClientSocket() { // behövs denna? just nu ja
        return clientSocket;
    }
}
