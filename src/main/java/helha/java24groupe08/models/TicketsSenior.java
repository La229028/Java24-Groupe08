package helha.java24groupe08.models;

public class TicketsSenior extends Tickets{
    private int ageRequirement;
    private double price = 7.5;

    public TicketsSenior(String movie, int personAge) {
        super("Senior", movie);
        ageRequirement = personAge;
        if(ageRequirement < 60){
            throw new IllegalArgumentException("Senior tickets are only for people aged 60 or older.");
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