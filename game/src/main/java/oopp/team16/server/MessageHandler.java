package oopp.team16.server;

import com.google.gson.Gson;

import java.io.*;

public abstract class MessageHandler {
    protected final Gson gson = new Gson();
    protected PrintWriter out;
    protected BufferedReader in;

    /**
     * Initializes input and output streams for the handler.
     *
     * @param inputStream  Input stream from the socket.
     * @param outputStream Output stream to the socket.
     * @throws IOException If an error occurs during initialization.
     */
    protected void initializeStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.out = new PrintWriter(outputStream, true);
        this.in = new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * Sends a GameMessage to the client or server.
     *
     * @param message The GameMessage to send.
     */
    public void sendMessage(GameMessage message) {
        if (out == null) {
            return;
        }
        if (message == null) {
            return;
        }
        try {
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);
            out.flush();
        } catch (Exception e) {
        }
    }

    /**
     * Receives a GameMessage from the client or server.
     *
     * @return The received GameMessage, or null if an error occurs.
     */
    public GameMessage receiveMessage() {
        try {
            String jsonMessage = in.readLine();
            if (jsonMessage != null) {
                return gson.fromJson(jsonMessage, GameMessage.class);
            } else {
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }

    /**
     * Closes the input and output streams.
     */
    public void closeStreams() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
        }
    }
}
