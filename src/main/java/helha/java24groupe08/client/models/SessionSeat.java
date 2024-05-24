package helha.java24groupe08.client.models;


public class SessionSeat {
    private int sessionID;
    private int seatID;
    private String seatNumber;
    private String status; // "free", "reserved", "taken"

    // Constructor
    public SessionSeat(int sessionID, int seatID) {
        this.sessionID = sessionID;
        this.seatID = seatID;
        this.status = "free"; // default status
    }

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



    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }


    public void updateSeatStatus(SessionSeat seat){
        // 1. establish connection to database

        // 2. update the status of the seat in the database
        // 3. execute sql query to update the status of the seat
        // 4. close the connection
    }
}