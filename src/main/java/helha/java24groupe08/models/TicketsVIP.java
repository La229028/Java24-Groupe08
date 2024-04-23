package helha.java24groupe08.models;

public class TicketsVIP extends Tickets {
    private double price = 15.0;

    public TicketsVIP(String movie) {
        super("VIP", movie);
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
