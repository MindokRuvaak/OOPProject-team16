package oopp.team16;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private int numPlayers;

    public GameServer() {
        System.out.println("Server started");
        numPlayers = 0;
        try {
            serverSocket = new ServerSocket(12345);
        } catch (IOException ex) {
            System.out.println("Server could not be started");
            ex.printStackTrace();
        }
    }

    public void acceptConnections() {
        System.out.println("Waiting for connections...");
        while (numPlayers < 2) {
            try {
                Socket clientSocket = serverSocket.accept();
                numPlayers++;
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


    //Broadcast a message to all connected clients
    public synchronized void broadcast(String message) {
    for (ClientHandler client : clients) {
        client.sendMessage(message);
        }
    }

    public static void main(String[] args){
        GameServer server = new GameServer();
        server.acceptConnections();
    }


    //Inner class to handle individual client connections
    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private GameServer server;

        public ClientHandler(Socket socket, GameServer server) {
            this.clientSocket = socket;
            this.server = server;
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

                    //Broadcast message to all clients
                    server.broadcast(message);

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
