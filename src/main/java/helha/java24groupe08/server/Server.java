package helha.java24groupe08.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static Server instance;
    private final int port = 12345;
    private final CopyOnWriteArrayList<ClientHandlerThread> clients;

    private Server() {
        clients = new CopyOnWriteArrayList<>();
    }

    public static synchronized Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandlerThread clientHandler = new ClientHandlerThread(clientSocket, this);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    public void disconnect(ClientHandlerThread clientHandler) {
        clients.remove(clientHandler);
    }
}
