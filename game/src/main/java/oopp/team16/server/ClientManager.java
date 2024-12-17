package oopp.team16.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientManager extends MessageHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientManager.class.getName());
    private final GameServer gameServer;
    private final String playerName;

    public ClientManager(Socket socket, GameServer gameServer, String playerName) throws IOException {
        initializeStreams(socket.getInputStream(), socket.getOutputStream());
        this.gameServer = gameServer;
        this.playerName = playerName;
    }

    @Override
    protected void onMessageReceived(GameMessage message) {
        logger.info("Message received from " + playerName + ": " + message.getType());
        gameServer.processClientMessage(message, this);
    }

    @Override
    public void run() {
        listenForMessages();
    }

    public void closeConnection() {
        closeStreams();
    }

    public String getClientName() {
        return playerName;
    }
}
