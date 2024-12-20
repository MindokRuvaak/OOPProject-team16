package oopp.team16.server;

import com.google.gson.Gson;

import java.io.*;
import java.util.logging.Logger;

public abstract class MessageHandler {
    private static final Logger logger = Logger.getLogger(MessageHandler.class.getName());
    protected final Gson gson = new Gson();
    protected PrintWriter out;
    protected BufferedReader in;

    protected abstract void onMessageReceived(GameMessage message);

    public void sendMessage(GameMessage message) {
        out.println(gson.toJson(message));
    }

    public void listenForMessages() {
        String jsonMessage;
        try {
            while ((jsonMessage = in.readLine()) != null) {
                GameMessage message = gson.fromJson(jsonMessage, GameMessage.class);
                onMessageReceived(message);
            }
        } catch (IOException e) {
            logger.info("Client disconnected: " + e.getMessage());
        }
    }

    protected void initializeStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.out = new PrintWriter(outputStream, true);
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        logger.fine("Streams initialized.");
    }

    public void closeStreams() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
        } catch (IOException e) {
            logger.warning("Error closing streams: " + e.getMessage());
        }
    }
}
