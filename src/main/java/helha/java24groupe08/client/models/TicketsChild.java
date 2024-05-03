package helha.java24groupe08.client.models;

public class TicketsChild extends Tickets{
    private int ageRequirement;
    private double price = 5.0;

    public TicketsChild(String movie, int personAge) {
        super("Child", movie);
        ageRequirement = personAge;
        if(ageRequirement >= 12){
            throw new IllegalArgumentException("Child tickets are only for people aged 11 or younger.");
        }else{
            setPrice(price);
        }
    }

    @Override
    public void getDetails() {
        System.out.println("Type: " + getType());
        System.out.println("Movie: " + getMovie());
        System.out.println("Price: " + price);
    }

    // GETTERS

    public int getAgeRequirement() { return ageRequirement; }
    public double getPrice() { return price; }
}