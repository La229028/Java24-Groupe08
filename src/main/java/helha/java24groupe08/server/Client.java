package helha.java24groupe08.server;

import helha.java24groupe08.common.network.Constants;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

/*
 * responsible for server connection
 */
public class Client {
    private final String username;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Client(String username) {
            this.username = username;
    }

    public void connectToServer() {
        try {
            setupConnection();
            sendUsername();
        } catch (ConnectException e){
            System.out.println("Error while connecting to server: Server is not available. Please try again later.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // Connects to the server
    private void setupConnection() throws IOException {
        if (socket == null) {
            socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT);
            System.out.println("Connected to the server");

            OutputStream out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            oos.flush();

            InputStream in = socket.getInputStream();
            ois = new ObjectInputStream(in);
        }
    }

    // Sends the username to the server
    private void sendUsername() throws IOException {
        oos.writeObject(username);
        oos.flush();
    }
}