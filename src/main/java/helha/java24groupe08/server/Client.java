package helha.java24groupe08.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

public class Client {
    private String username;

    public Client(String username) {
        this.username = username;
    }

    public void connectToServer(){
        try {
            Socket socket = new Socket("localhost", 6500);
            System.out.println("Connected to the server");

            InputStream in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(username);

            Object response = ois.readObject();
            System.out.println("Response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        try {
//            Socket socket = new Socket("localhost", 6500);
//            System.out.println("Connected to the server");
//
//            try (InputStream in = socket.getInputStream();
//                 ObjectInputStream ois = new ObjectInputStream(in)) {
//                final Hashtable clientTable = (Hashtable) ois.readObject();
//
//                System.out.println(clientTable.get("test"));
//            } catch (ClassNotFoundException e){
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}