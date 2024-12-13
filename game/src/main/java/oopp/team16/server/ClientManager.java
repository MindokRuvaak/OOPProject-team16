package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

//ClientManager hanterar individuella klienter, från serversidan.
public class ClientManager extends MessageHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());

    private final ConnectionManager connectionManager;
    private final GameServer gameServer;
    private final Socket socket;
    private final int id; //Gör id något förutom logging just nu? typ nej. måste linka player med id tror jag

    public ClientManager(Socket socket, ConnectionManager connectionManager, GameServer gameServer, int id) throws IOException {
        this.connectionManager = connectionManager;
        this.socket = socket;
        this.gameServer = gameServer;
        this.id = id;

        initializeStreams(socket.getInputStream(), socket.getOutputStream());
        logger.info("ClientManager created for player " + id + " at " + socket.getRemoteSocketAddress());
    }

    @Override
    protected void onMessageReceived(GameMessage message) {
        gameServer.processClientMessage(message);
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
            socket.close();
            connectionManager.removeClient(this);
        } catch (IOException e) {
            logger.warning(String.format("Error closing connection for player %d: %s", id, e.getMessage()));
        }
    }

    public int getClientId() {
        return id;
    }
}
