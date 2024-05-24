package helha.java24groupe08.server;

import helha.java24groupe08.client.models.SeatReservationManager;

import java.io.IOException;
import java.io.ObjectInputStream;


import helha.java24groupe08.client.models.SeatReservationManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandlerThread extends Thread {
    private boolean running = true;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final Server server;

    public ClientHandlerThread(Socket clientSocket, Server server) throws IOException {
        this.ois = new ObjectInputStream(clientSocket.getInputStream());
        this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
        this.server = server;
    }

    @Override
    public void run() {
        try {
            handleClientCommunication();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Communication error: " + e.getMessage());
        }
    }

    private void handleClientCommunication() throws IOException, ClassNotFoundException {
        String username = (String) ois.readObject();
        System.out.println("Hello " + username + "!");

        while (running) {
            Object obj = ois.readObject();
            if (obj instanceof String) {
                String message = (String) obj;
                if ("RESERVE_SEAT".equals(message)) {
                    int sessionId = (int) ois.readObject();
                    String seatNumber = (String) ois.readObject();
                    boolean result = SeatReservationManager.getInstance().reserveSeat(String.valueOf(sessionId), seatNumber);
                    oos.writeObject(result); // Send the result back to the client
                    oos.flush();
                    System.out.println("Seat reserved: " + result);
                } else if ("DISCONNECT".equals(message)) {
                    server.disconnect(this);
                    running = false;
                } else {
                    System.out.println("Received unknown message: " + message);
                }
            }
        }
    }
}
