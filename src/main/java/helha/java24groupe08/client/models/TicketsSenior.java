package helha.java24groupe08.client.models;

/**
 * This class represents a senior ticket.
 * It extends the Tickets class.
 */
public class TicketsSenior extends Tickets{
    private final double price = 7.5;

    public TicketsSenior() {
        super("Senior");
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