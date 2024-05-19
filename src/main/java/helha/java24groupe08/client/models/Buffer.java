package helha.java24groupe08.client.models;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private static Buffer instance;
    private List<TicketInfo> cart;

    private Buffer() {
        cart = new ArrayList<>();
    }

    public static Buffer getInstance() {
        if (instance == null) {
            instance = new Buffer();
        }
        return instance;
    }

    public void addToCart(TicketInfo ticketInfo) {
        cart.add(ticketInfo);
    }

    public List<TicketInfo> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}

