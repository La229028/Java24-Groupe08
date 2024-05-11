package helha.java24groupe08.server;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;


public class server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6500);
            System.out.println("Server started on port " + 6500);

            while(true){
                Socket client = serverSocket.accept();
                System.out.println("New connection from " + client.getInetAddress() + ". Client is now connected !!");

                new Thread(() -> {
                    try {
                        Hashtable clientTable = new Hashtable();
                        clientTable.put("test", "Je suis le test !");

                        OutputStream out = client.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(out);
                        oos.writeObject(clientTable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
//
//
//
//            Hashtable clientTable = new Hashtable();
//            clientTable.put("test", "Je suis le test !");
//
//            OutputStream out = client.getOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(out);
//            oos.writeObject(clientTable);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}