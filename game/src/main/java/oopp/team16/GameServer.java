package oopp.team16;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());
    private final ServerSocket serverSocket;
    private final ConnectionController connectionController;
    private final GameActionController gameActionController;

    public GameServer(GameModel model, int maxPlayers, int port) throws IOException {
        serverSocket = new ServerSocket(port);
        logger.info("Server started on port " + port);

        // Initialize controllers
        connectionController = new ConnectionController(serverSocket, maxPlayers, model);
        gameActionController = new GameActionController(model, connectionController);
    }

    public void acceptConnections() {
        logger.info("Waiting for connections...");
        connectionController.acceptConnections();
    }

    public void stopServer() {
        logger.info("Stopping server...");
        connectionController.stopConnections();
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error while closing server socket", ex);
        }
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 12345;
        int maxPlayers = args.length > 1 ? Integer.parseInt(args[1]) : 4;

        try {
            GameModel gameModel = new GameModel();
            GameServer server = new GameServer(gameModel, maxPlayers, port);
            server.acceptConnections();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to start server", ex);
        }
    }
}
