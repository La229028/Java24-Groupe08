package helha.java24groupe08.client.models;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Buffer {
    private static Buffer instance;
    private Set<TicketInfo> cart;

    private Buffer() {
        cart = new HashSet<>();
    }

    public static synchronized Buffer getInstance() {
        if (instance == null) {
            instance = new Buffer();
        }
        return instance;
    }

    public synchronized void addToCart(TicketInfo ticketInfo) {
        cart.add(ticketInfo);
    }

    public synchronized boolean isSeatInCart(String date, String time, String room, String seatNumber) {
        return cart.stream().anyMatch(ticket ->
                ticket.getDate().equals(date) &&
                        ticket.getTime().equals(time) &&
                        ticket.getRoom().equals(room) &&
                        ticket.getSeatNumber().equals(seatNumber)
        );
    }

    public synchronized Set<TicketInfo> getCart() {
        return new HashSet<>(cart);
    }

    public synchronized List<TicketInfo> getTicketsForSession(Session session) {
        String date = session.getDate().toString();
        String time = session.getStartTime().toString();
        String room = "Room " + session.getRoomNumber();

        return cart.stream()
                .filter(ticket ->
                        ticket.getDate().equals(date) &&
                                ticket.getTime().equals(time) &&
                                ticket.getRoom().equals(room)
                )
                .collect(Collectors.toList());
    }

    public void clearCart() {
        cart.clear();
    }
}



