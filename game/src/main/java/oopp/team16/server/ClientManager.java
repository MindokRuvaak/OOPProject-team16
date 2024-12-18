package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager extends MessageHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());

    private final GameServer gameServer;
    private final Socket socket;
    private final int id;

    public ClientManager(Socket socket, GameServer gameServer, int id) throws IOException {

        this.gameServer = gameServer;
        this.socket = socket;
        this.id = id;

        initializeStreams(socket.getInputStream(), socket.getOutputStream());
        logger.info("ClientManager created for player " + id + " at " + socket.getRemoteSocketAddress());
    }

    @Override
    protected void onMessageReceived(GameMessage message) {
        logger.info("Message received from player " + id + ": " + message.getType());
        try {
            gameServer.processClientMessage(message);
        } catch (Exception e) {
            logger.warning("Error processing message from player " + id + ": " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            listenForMessages();
        } catch (Exception e) {
            logger.warning("Connection lost with player " + id + ": " + e.getMessage());
        } finally {
            closeConnection();
            }
        }

    public void closeConnection() {
        try {
            closeStreams();
            if (!socket.isClosed()) {
                socket.close();
            }
            logger.info("Connection closed for player " + id);
        } catch (IOException e) {
            logger.warning("Error closing connection for player " + id + ": " + e.getMessage());
        }
    }

    public int getClientId() {
        return id;
    }
}
