package oopp.team16.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientManager(Socket socket, ConnectionController connectionController) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                logger.info("Received message from client: " + message);

                            }
        } catch (IOException ex) {
            logger.warning("Client disconnected: " + ex.getMessage());
        } finally {
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
}
