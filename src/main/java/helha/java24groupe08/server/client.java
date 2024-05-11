package helha.java24groupe08.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Hashtable;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6500);
            System.out.println("Connected to the server");

            InputStream in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);

            // Read the object from the server
            final Hashtable clientTable = (Hashtable) ois.readObject();

            System.out.println(clientTable.get("test"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}