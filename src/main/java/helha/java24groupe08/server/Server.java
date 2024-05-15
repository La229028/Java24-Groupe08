package helha.java24groupe08.server;

import helha.java24groupe08.client.models.Reservation;
import helha.java24groupe08.client.models.SeatReservationRequest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Hashtable;


public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6500)) {
            System.out.println("Server started on port " + 6500);

            while(true) {
                Socket client = serverSocket.accept();
                System.out.println("New connection from " + client.getInetAddress() + ". Client is now connected !!");

                new Thread(() -> {
                    try (OutputStream out = client.getOutputStream();
                         ObjectOutputStream oos = new ObjectOutputStream(out);
                         InputStream in = client.getInputStream();
                         ObjectInputStream ois = new ObjectInputStream(in)) {

                        String username = (String) ois.readObject();
                        System.out.println("Bonjour " + username + "!");

                        Hashtable clientTable = new Hashtable();
                        synchronized (clientTable) {
                            clientTable.put("test", "Récupération du username !");
                            oos.writeObject(clientTable);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}