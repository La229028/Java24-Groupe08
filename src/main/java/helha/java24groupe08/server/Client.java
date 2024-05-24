package helha.java24groupe08.server;

import helha.java24groupe08.client.models.SeatReservationManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String username;

    public Client(String username) {
        this.username = username;
    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(username);
            oos.flush();

            // Start a thread to listen for server updates
            new Thread(this::listenForServerUpdates).start();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean reserveSeat(int sessionId, String seatNumber) {
        try {
            oos.writeObject("RESERVE_SEAT");
            oos.writeObject(sessionId);
            oos.writeObject(seatNumber);
            oos.flush();

            // Read the response from the server
            return (boolean) ois.readObject(); // Expecting a boolean response
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while reserving seat: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private void listenForServerUpdates() {
        try {
            while (true) {
                Object obj = ois.readObject();
                if (obj instanceof String) {
                    String message = (String) obj;
                    if ("UPDATE".equals(message)) {
                        // Trigger UI update
                        SeatReservationManager.getInstance().notifyObservers();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while listening for server updates: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                oos.writeObject("DISCONNECT");
                oos.flush();
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error while disconnecting: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
