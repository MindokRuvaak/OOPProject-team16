package oopp.team16.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class StartupManager {

    /**
     * Starts a server socket on the specified port.
     *
     * @param port The port on which the server will listen.
     * @return A ServerSocket instance if successful, or null if an error occurs.
     */
    public ServerSocket startServer(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"));
        } catch (IOException e) {
        }
        return serverSocket;
    }
}
