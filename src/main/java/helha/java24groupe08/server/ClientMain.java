package helha.java24groupe08.server;

public class ClientMain {
    public static void main(String[] args) {
        Client client1 = new Client("User1");
        client1.connectToServer();
        client1.reserveSeat(1, "A1");

        Client client2 = new Client("User2");
        client2.connectToServer();
        client2.reserveSeat(1, "A2");
    }
}
