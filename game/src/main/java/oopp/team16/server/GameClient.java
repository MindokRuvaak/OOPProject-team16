package oopp.team16.server;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public GameClient(String serverAddress, int port) {
        connectToServer(serverAddress, port);
    }

    private void connectToServer(String serverAddress, int port) {
        try {
            clientSocket = new Socket(serverAddress, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            logger.info("Connected to " + serverAddress + ":" + port);
        } catch (IOException e) {
            logger.severe("Error connecting to server: " + e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    public void closeConnection() { // detta är för om klienten vill closea connection. ska kallas på av en controller
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            logger.severe("Error closing connection: " + e.getMessage());
        }
    }

    public void sendMessage(GameMessage message) {
        try {
            if (out != null) {
                Gson gson = new Gson();
                String jsonMessage = gson.toJson(message);
                out.println(jsonMessage);
                out.flush();
            } else {
                logger.warning("Output stream is null.");
            }
        } catch (Exception e) {
            logger.severe("Error sending message: " + e.getMessage());
        }
    }


    public String receiveMessage() {
        try {
            if (in != null) {
                return in.readLine(); // Reads a single line from the server
            } else {
                logger.warning("Input stream is null.");
            }
        } catch (IOException e) {
            logger.severe("Error reading from server: " + e.getMessage());
        }
        return null;
    }
}
