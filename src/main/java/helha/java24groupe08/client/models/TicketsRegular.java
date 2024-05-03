package helha.java24groupe08.client.models;

public class TicketsRegular extends Tickets{
    private double price = 11.0;

    public TicketsRegular(String movie) {
        super("Regular", movie);
        setPrice(price);
    }

    @Override
    public void getDetails() {
        System.out.println("Type: " + getType());
        System.out.println("Movie: " + getMovie());
        System.out.println("Price: " + price);
    }

    // GETTERS

    public double getPrice() { return price; }

}