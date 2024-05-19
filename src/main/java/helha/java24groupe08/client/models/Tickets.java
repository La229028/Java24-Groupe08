package helha.java24groupe08.client.models;

public class Tickets implements TicketComponent{
    private String type;
    private String movie;
    private double price;

    public Tickets(String type, String movie) {
        this.type = type;
        this.movie = movie;
    }

    @Override
    public void getDetails() {
        System.out.println("Type: " + type);
        System.out.println("Movie: " + movie);
        System.out.println("Price: " + price);
    }

    // GETTERS

    public String getType() { return type; }
    public String getMovie() { return movie; }
    @Override
    public double getPrice() { return price; }


    // SETTERS

    public void setType(String type) { this.type = type; }
    public void setMovie(String movie) { this.movie = movie; }
    public void setPrice(double price) { this.price = price; }

}