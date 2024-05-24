package helha.java24groupe08.client.models;

/**
 * This class represents a child ticket.
 * It extends the Tickets class.
 */
public class TicketsChild extends Tickets{
    private final double price = 5.0;

    public TicketsChild() {
        super("Child");
        setPrice(price);
    }

    @Override
    public void getDetails() {
        System.out.println("Type: " + getType());
        System.out.println("Price: " + price);
    }

    // GETTER
    public double getPrice() { return price; }
}