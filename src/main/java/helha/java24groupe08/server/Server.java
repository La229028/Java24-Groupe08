package helha.java24groupe08.server;

import helha.java24groupe08.common.network.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Server instance;
    private static List<ClientHandlerThread> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            startServer();
        } catch (IOException e) {
            System.out.println("Error while starting server" + e.getMessage());
            e.printStackTrace();
        }
    }

    //method to start the server
    private static void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            System.out.println("Server started on port " + Constants.SERVER_PORT);

            while(true) {
                acceptNewClient(serverSocket);
            }
        }
    }

    // Accepts new client connections
    private static void acceptNewClient(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        System.out.println("New connection from " + socket.getInetAddress() + ". Client is now connected !!");

        ClientHandlerThread clientThread = new ClientHandlerThread(socket);
        clients.add(clientThread);
        clientThread.start();
    }

    // Singleton pattern
    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    // Disconnects a client
    public void disconnect(ClientHandlerThread clientThread) {
        clientThread.stopRunning();
        clients.remove(clientThread);
    }
}