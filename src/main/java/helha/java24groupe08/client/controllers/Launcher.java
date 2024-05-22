package helha.java24groupe08.client.controllers;

import java.io.IOException;
import java.net.Socket;

/**
 * This is the launcher class for the application.
 * It calls the main method of the IndexApplication class to start the application.
 */
public class Launcher {

    /**
     * The main method of the launcher class.
     * It calls the main method of the IndexApplication class to start the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6500);
            System.out.println("Connected to the server");

            IndexController.main(args);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
