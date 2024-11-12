package oopp.team16;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameServer implements Observer{

    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private GameModel gameModel; //Vi vill observa gamemodel

    public GameServer(GameModel model) {
        this.gameModel = model;
        model.addObserver(this);

        try {
            int port = 12345;
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

        } catch (IOException ex) {
            System.out.println("Server could not be started");
            ex.printStackTrace();
        }
    }

    public void acceptConnections() {
        System.out.println("Waiting for connections...");
        while (clients.size() < 2) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                //Create a new handler for each client and start a new thread.
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();

            } catch (IOException ex) {
                System.out.println("Error accepting connection");
                ex.printStackTrace();
            }
        }
        System.out.println("Connections accepted");
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GameModel) {
            String updatedState = (String) arg;
            broadcast(updatedState);  // Broadcast model state to all clients
        }
    }


    //Broadcast a message to all connected clients
    public synchronized void broadcast(String message) {
    for (ClientHandler client : clients) {
        client.sendMessage(message);
        }
    }


    //Inner class to handle individual client connections
    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket, GameServer server) {
            this.clientSocket = socket;
        }


        @Override
        public void run(){
            try {
                //Set up I/O Streams
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                //Handle messages from client
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received message from client: " + message);

                    // Update game state through the model (controller should normally do this)
                    //gameModel.updateGameState(message);

                }
            } catch (IOException ex) {
                System.out.println("Error handling client connection");
                ex.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket");
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
