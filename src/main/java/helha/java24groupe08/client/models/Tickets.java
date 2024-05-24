package helha.java24groupe08.client.models;

/**
 * This class represents a ticket.
 * It implements the TicketComponent interface.
 */
public class Tickets implements TicketComponent{
    private String type;
    private double price;

    /**
     * Constructor for the Tickets class.
     * @param type The type of the ticket.
     */
    public Tickets(String type) {
        this.type = type;
    }

    /**
     * Prints the details of the ticket.
     */
    @Override
    public void getDetails() {
        System.out.println("Type: " + type);
        System.out.println("Price: " + price);
    }

    // GETTERS
    public String getType() { return type; }
    @Override
    public double getPrice() { return price; }


    // SETTERS
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }

}