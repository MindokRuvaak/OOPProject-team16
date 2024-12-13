package oopp.team16.server;

import com.google.gson.Gson;

import java.io.*;
import java.net.SocketException;
import java.util.logging.Logger;

public abstract class MessageHandler {
    private static final Logger logger = Logger.getLogger(MessageHandler.class.getName());
    protected final Gson gson = new Gson();
    protected PrintWriter out;
    protected BufferedReader in;

    protected void initializeStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.out = new PrintWriter(outputStream, true);
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        logger.fine("Streams initialized.");
    }

    public void sendMessage(GameMessage message) {
        String jsonMessage = gson.toJson(message);
        out.println(jsonMessage);
        logger.fine("Sent message: " + jsonMessage);
    }

    public GameMessage receiveMessage() {
        try {
            String jsonMessage = in.readLine();
            if (jsonMessage != null) {
                return gson.fromJson(jsonMessage, GameMessage.class);
            }
        } catch (SocketException e) {
            logger.fine("Socket closed while reading messages (expected during shutdown)." + e.getMessage());
        } catch (IOException e) {
            logger.warning("Error reading message" + e.getMessage());
        }
        return null;
    }

    public void closeStreams() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            logger.warning("Error while closing streams." + e.getMessage());
        }
        logger.info("Streams closed.");
    }
}
