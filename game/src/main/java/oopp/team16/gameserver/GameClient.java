package oopp.team16.gameserver;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private final String serverAddress;
    private final int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private volatile boolean running = true;

    public GameClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * Establishes a connection to the server.
     */
    public void connect() {
        try {
            clientSocket = new Socket(serverAddress, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            logger.info("Connected to server at " + serverAddress + ":" + port);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to server", e);
            throw new RuntimeException("Could not connect to server", e);
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     */
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        } else {
            logger.warning("Attempted to send a message but the output stream is null.");
        }
    }

    /**
     * Listens for updates from the server in a separate thread.
     *
     * @param handler a handler to process server messages.
     */
    public void listenForUpdates(UpdateHandler handler) {
        new Thread(() -> {
            try {
                String serverMessage;
                while (running && (serverMessage = in.readLine()) != null) {
                    handler.handleServerUpdate(serverMessage);
                }
            } catch (IOException e) {
                if (running) {
                    logger.log(Level.WARNING, "Error receiving updates from server", e);
                }
            } finally {
                closeConnection();
            }
        }).start();
    }

    /**
     * Closes the connection to the server.
     */
    public void closeConnection() {
        running = false;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            logger.info("Client connection closed.");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error closing client connection", e);
        }
    }

    /**
     * Interface for handling server updates.
     */
    public interface UpdateHandler {
        void handleServerUpdate(String message);
    }
}
