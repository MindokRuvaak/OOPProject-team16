package oopp.team16.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());
    private final Socket clientSocket;
    private PrintWriter out;

    public ClientManager(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                logger.info("Received message from client: " + message);
            }
        } catch (IOException ex) {
            logger.warning("Client disconnected or error occurred: " + ex.getMessage());
        } finally {
            // Log when connection is being closed
            logger.info("Closing connection for client: " + clientSocket.getInetAddress().getHostAddress());
            closeConnection();
        }
    }

    public void closeConnection() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            //connectionController.removeClient(this);
        } catch (IOException ex) {
            logger.warning("Error closing client connection: " + ex.getMessage());
        }
    }

    //public void removeClient(){} behövs denna? kanske bra att ha. graceful shutdown yadda yadda
}
