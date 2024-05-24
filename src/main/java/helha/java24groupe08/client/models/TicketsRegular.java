package helha.java24groupe08.client.models;

/**
 * This class represents a regular ticket.
 * It extends the Tickets class.
 */
public class TicketsRegular extends Tickets{
    private final double price = 11.0;

    public TicketsRegular() {
        super("Regular");
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