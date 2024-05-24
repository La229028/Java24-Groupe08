package helha.java24groupe08.client.controllers;

public class Launcher {

    public static void main(String[] args) {
        // Initialize the main application thread
        ApplicationThread appThread = new ApplicationThread();
        appThread.start();

        // Launch the JavaFX application
        IndexController.main(args);
    }
}

class ApplicationThread extends Thread {
    @Override
    public void run() {
        // Main application logic
        System.out.println("Application thread running...");
    }
}
