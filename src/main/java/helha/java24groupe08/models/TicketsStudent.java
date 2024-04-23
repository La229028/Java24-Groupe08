package helha.java24groupe08.models;

public class TicketsStudent extends Tickets{
    private String schoolName;
    private double price = 7.5;

    public TicketsStudent(String movie, String schoolName) {
        super("Student", movie);
        this.schoolName = schoolName;
        if(schoolName == null || schoolName.isEmpty()){
            throw new IllegalArgumentException("School name cannot be empty.");
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

    public String getSchoolName() { return schoolName; }
    public double getPrice() { return price; }
}
