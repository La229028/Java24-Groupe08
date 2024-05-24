package helha.java24groupe08.server;

import java.io.*;
import java.net.Socket;

// This class is responsible for handling communication with a client.
public class ClientHandlerThread extends Thread {
    private Socket client;
    private boolean running = true;

    public ClientHandlerThread(Socket client) {
        this.client = client;
    }

    // Main communication method
    @Override
    public void run() {
        try (OutputStream out = client.getOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out);
             InputStream in = client.getInputStream();
             ObjectInputStream ois = new ObjectInputStream(in)) {

            oos.flush();

            while (running) {
                handleClientCommunication(ois);
            }

        } catch (IOException e) {
            if (!this.isInterrupted()) {
                System.err.println("Connection lost with client due to network error: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error deserializing object: " + e.getMessage());
        }
    }

    // Processes client messages
    private void handleClientCommunication(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        String username = (String) ois.readObject();
        System.out.println("Bonjour " + username + "!");



        String message = (String) ois.readObject();
        if ("DISCONNECT".equals(message)) {
            Server.getInstance().disconnect(this);
            running = false;
        } else {
            System.out.println("Bonjour " + message + "!");
        }
    }

    // Stops the thread
    public void stopRunning() {
        this.running = false;
    }
}