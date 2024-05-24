package helha.java24groupe08.client.models;


public class SessionSeat {
    private int sessionID;
    private int seatID;
    private String seatNumber;
    private String status; // "free", "reserved", "taken"


    public SessionSeat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.status = "free"; // default status
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and setters
    public int getId() {
        return seatID;
    }




}