package helha.java24groupe08.client.models;

/**
 * This class represents a student ticket.
 * It extends the Tickets class.
 */
public class TicketsStudent extends Tickets{
    private final double price = 7.5;

    public TicketsStudent() {
        super("Student");
        setPrice(price);
    }

    @Override
    public void getDetails() {
        System.out.println("Type: " + getType());
        System.out.println("Price: " + price);
    }

    // GETTERS
    public double getPrice() { return price; }
}